package com.rvcoding.imhere.routes

//import io.ktor.server.routing.RoutingContext
import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.AuthResult.LoginResult
import com.rvcoding.imhere.domain.data.api.AuthResult.LogoutResult
import com.rvcoding.imhere.domain.data.api.AuthResult.RegisterResult
import com.rvcoding.imhere.domain.data.api.request.LoginRequest
import com.rvcoding.imhere.domain.data.api.request.LogoutRequest
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
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.component3
import kotlin.collections.component4
import kotlin.collections.set


fun Routing.authentication() {
    val authRepository: ApiAuthRepository = get<ApiAuthRepository>()
    val sessionRepository: ApiSessionRepository = get<ApiSessionRepository>()
    val userRepository: ApiUserRepository = get<ApiUserRepository>()
    val isInternal: MutableMap<String, Boolean> = Collections.synchronizedMap(mutableMapOf<String, Boolean>())

    post(Route.Register.endpoint) {
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
                is RegisterResult.UnauthorizedError -> onUnauthorizedError(isLogout = false)
                is RegisterResult.UserAlreadyRegisteredError -> {
                    isInternal[userId] = true
                    call.respondRedirect(url = "${Route.LoginInternal.endpoint}?userId=$userId&password=$password", permanent = false)
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
        } catch (e: Exception) { onUnauthorizedError(isLogout = false)
        }
    }

    post(Route.Login.endpoint) {
        val request = call.receive<LoginRequest>()
        try {
            val (userId, password) = listOf(request.userId, request.password)
            loginHandle(authRepository, sessionRepository, userRepository, userId, password)
        } catch (e: Exception) { onUnauthorizedError(isLogout = false)
        }
    }
    post(Route.Logout.endpoint) {
        val request = call.receive<LogoutRequest>()
        try {
            when (val result = authRepository.logout(request.userId)) {
                LogoutResult.InvalidParametersError -> {
                    call.respond(
                        message = AuthResponse(
                            code = result.code,
                            type = result::class.simpleName.toString(),
                            message = "User logout failed.",
                            result = result
                        ),
                        status = HttpStatusCode.BadRequest
                    )
                }
                LogoutResult.UnauthorizedError -> onUnauthorizedError(isLogout = true)
                LogoutResult.Success -> {
                    call.respond(
                        message = AuthResponse(
                            code = result.code,
                            type = result::class.simpleName.toString(),
                            message = "User logged out successfully.",
                            result = result
                        ),
                        status = HttpStatusCode.OK
                    )
                }
            }
        } catch (e: Exception) { onUnauthorizedError(isLogout = true) }
    }

    post(Route.LoginInternal.endpoint) {
        val userId = call.request.queryParameters["userId"] ?: ""
        if (isInternal[userId] == false) {
            onUnauthorizedError(isLogout = false)
            return@post
        }
        else { isInternal[userId] = false }
        val password = call.request.queryParameters["password"] ?: ""
        loginHandle(authRepository, sessionRepository, userRepository, userId, password)
    }
    get(Route.LoginInternal.endpoint) {
        val userId = call.request.queryParameters["userId"] ?: ""
        if (isInternal[userId] == false) {
            onUnauthorizedError(isLogout = false)
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
        LoginResult.UnauthorizedError -> onUnauthorizedError(isLogout = false)
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
private suspend fun PipelineContext<Unit, ApplicationCall>.onUnauthorizedError(isLogout: Boolean) {
    call.respond(
        message = AuthResponse(
            code = if (isLogout) LogoutResult.UnauthorizedError.code else LoginResult.UnauthorizedError.code,
            type = if (isLogout) LogoutResult.UnauthorizedError::class.simpleName.toString() else LoginResult.UnauthorizedError::class.simpleName.toString(),
            message = "User unauthorized.",
            result = if (isLogout) LogoutResult.UnauthorizedError else LoginResult.UnauthorizedError
        ),
        status = HttpStatusCode.Unauthorized
    )
}
