package com.rvcoding.imhere.routes

//import io.ktor.server.routing.RoutingContext
import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.AuthResult.LoginResult
import com.rvcoding.imhere.domain.data.api.AuthResult.RegisterResult
import com.rvcoding.imhere.domain.data.api.request.LoginRequest
import com.rvcoding.imhere.domain.data.api.request.RegisterRequest
import com.rvcoding.imhere.domain.data.api.response.AuthResponse
import com.rvcoding.imhere.domain.data.db.SessionEntity
import com.rvcoding.imhere.domain.repository.ApiAuthRepository
import com.rvcoding.imhere.domain.repository.ApiSessionRepository
import com.rvcoding.imhere.domain.repository.ApiUserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.util.pipeline.PipelineContext
import org.koin.ktor.ext.get
import java.util.Collections


fun Routing.authentication() {
    val authRepository: ApiAuthRepository = get<ApiAuthRepository>()
    val sessionRepository: ApiSessionRepository = get<ApiSessionRepository>()
    val userRepository: ApiUserRepository = get<ApiUserRepository>()
    val isInternal: MutableMap<String, Boolean> = Collections.synchronizedMap(mutableMapOf<String, Boolean>())

    post(Route.Register.path) {
        val request = call.receive<RegisterRequest>()
        try {
            val (userId, password, firstName, lastName) = listOf(request.userId, request.password, request.firstName, request.lastName)
            when (val result = authRepository.register(userId, password, firstName, lastName)) {
                is RegisterResult.InvalidParametersError, is RegisterResult.CriteriaNotMetError -> call.respond(
                    message = AuthResponse(
                        code = result.code,
                        type = result::class.simpleName.toString(),
                        message = "User registration failed.",
                        result = result
                    ),
                    status = HttpStatusCode.BadRequest
                )
                is RegisterResult.UnauthorizedError -> onUnauthorizedError()
                is RegisterResult.UserAlreadyRegisteredError -> {
                    isInternal[userId] = true
                    call.respondRedirect(url = "${Route.LoginInternal.path}?userId=$userId&password=$password", permanent = false)
                }
                is RegisterResult.Success -> call.respond(
                    message = AuthResponse(
                        code = result.code,
                        type = result::class.simpleName.toString(),
                        message = "User registered successfully.",
                        result = result
                    ),
                    status = HttpStatusCode.OK
                )
            }
        } catch (e: Exception) { onUnauthorizedError() }
    }

    post(Route.Login.path) {
        val request = call.receive<LoginRequest>()
        try {
            val (userId, password) = listOf(request.userId, request.password)
            loginHandle(authRepository, sessionRepository, userRepository, userId, password)
        } catch (e: Exception) { onUnauthorizedError() }
    }
    get(Route.LoginInternal.path) {
        val userId = call.request.queryParameters["userId"] ?: ""
        if (isInternal[userId] == false) {
            onUnauthorizedError()
            return@get
        }
        else { isInternal[userId] = false }
        val password = call.request.queryParameters["password"] ?: ""
        loginHandle(authRepository, sessionRepository, userRepository, userId, password)
    }

//    post("/$companyId/changepassword") {
//    }

}


//private suspend fun RoutingContext.loginHandle(
private suspend fun PipelineContext<Unit, ApplicationCall>.loginHandle(
    authRepository: ApiAuthRepository,
    sessionRepository: ApiSessionRepository,
    userRepository: ApiUserRepository,
    userId: String,
    password: String
) {
    when (val result = authRepository.login(userId, password)) {
        LoginResult.CredentialsMismatchError -> call.respond(
            message = AuthResponse(
                code = result.code,
                type = result::class.simpleName.toString(),
                message = "User login failed.",
                result = result
            ),
            status = HttpStatusCode.PreconditionFailed
        )
        LoginResult.UnauthorizedError -> onUnauthorizedError()
        LoginResult.InvalidParametersError -> call.respond(
            message = AuthResponse(
                code = result.code,
                type = result::class.simpleName.toString(),
                message = "User login failed.",
                result = result
            ),
            status = HttpStatusCode.BadRequest
        )
        LoginResult.Success -> {
            sessionRepository.newSession(SessionEntity.generate(userId))
            userRepository.updateLastLogin(userId)
            call.respond(
                message = AuthResponse(
                    code = result.code,
                    type = result::class.simpleName.toString(),
                    message = "User logged in successfully.",
                    result = result
                ),
                status = HttpStatusCode.OK
            )
        }
    }
}

//private suspend fun RoutingContext.onUnauthorizedError() {
private suspend fun PipelineContext<Unit, ApplicationCall>.onUnauthorizedError() {
    call.respond(
        message = AuthResponse(
            code = LoginResult.UnauthorizedError.code,
            type = LoginResult.UnauthorizedError::class.simpleName.toString(),
            message = "User unauthorized.",
            result = LoginResult.UnauthorizedError
        ),
        status = HttpStatusCode.Unauthorized
    )
}
