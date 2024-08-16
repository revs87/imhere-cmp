package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.repository.AuthRepository
import com.rvcoding.imhere.domain.repository.LoginResult
import com.rvcoding.imhere.domain.repository.RegisterResult
import com.rvcoding.imhere.domain.response.AuthResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
//import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject


fun Routing.authentication() {
    val authRepository: AuthRepository by inject()

    var isInternal = false


    post(Route.Register.path) {
        val request = call.receive<AuthRequest>()
        try {
            val (userId, password) = listOf(request.userId, request.password)
            when (val result = authRepository.register(userId, password)) {
                is RegisterResult.InvalidParametersError, is RegisterResult.CriteriaNotMetError -> call.respond(
                    message = AuthResponse(result.code, result::class.simpleName.toString(), "User registration failed."),
                    status = HttpStatusCode.BadRequest
                )
                is RegisterResult.UnauthorizedError -> onUnauthorizedError()
                is RegisterResult.UserAlreadyRegisteredError -> {
                    isInternal = true
                    call.respondRedirect(url = "${Route.LoginInternal.path}?userId=$userId&password=$password", permanent = false)
                }
                is RegisterResult.Success -> call.respond(
                    message = AuthResponse(result.code, result::class.simpleName.toString(), "User registered successfully."),
                    status = HttpStatusCode.OK
                )
            }
        } catch (e: Exception) { onUnauthorizedError() }
    }

    post(Route.Login.path) {
        val request = call.receive<AuthRequest>()
        try {
            val (userId, password) = listOf(request.userId, request.password)
            loginHandle(authRepository, userId, password)
        } catch (e: Exception) { onUnauthorizedError() }
    }
    get(Route.LoginInternal.path) {
        if (!isInternal) {
            onUnauthorizedError()
            return@get
        }
        else { isInternal = false }
        val userId = call.request.queryParameters["userId"] ?: ""
        val password = call.request.queryParameters["password"] ?: ""
        loginHandle(authRepository, userId, password)
    }

//    post("/$companyId/changepassword") {
//    }

}


//private suspend fun RoutingContext.loginHandle(
private suspend fun PipelineContext<Unit, ApplicationCall>.loginHandle(
    authRepository: AuthRepository,
    userId: String,
    password: String
) {
    when (val result = authRepository.login(userId, password)) {
        LoginResult.CredentialsMismatchError -> call.respond(
            message = AuthResponse(result.code, result::class.simpleName.toString(), "User login failed."),
            status = HttpStatusCode.PreconditionFailed
        )
        LoginResult.UnauthorizedError -> onUnauthorizedError()
        LoginResult.InvalidParametersError -> call.respond(
            message = AuthResponse(result.code, result::class.simpleName.toString(), "User login failed."),
            status = HttpStatusCode.BadRequest
        )
        LoginResult.Success -> call.respond(
            message = AuthResponse(result.code, result::class.simpleName.toString(), "User logged in successfully."),
            status = HttpStatusCode.OK
        )
    }
}

//private suspend fun RoutingContext.onUnauthorizedError() {
private suspend fun PipelineContext<Unit, ApplicationCall>.onUnauthorizedError() {
    call.respond(
        message = AuthResponse(
            LoginResult.UnauthorizedError.code,
            LoginResult.UnauthorizedError::class.simpleName.toString(),
            "User unauthorized."
        ),
        status = HttpStatusCode.Unauthorized
    )
}

@Serializable
data class AuthRequest(
    val userId: String,
    val password: String
)
