package com.rvcoding.imhere.plugins

import com.rvcoding.imhere.domain.data.api.AuthResult
import com.rvcoding.imhere.domain.data.api.response.AuthResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(
                message = AuthResponse(
                    code = Error.NotFoundError.code,
                    type = Error.NotFoundError::class.simpleName.toString(),
                    message = "Not found.",
                    result = AuthResult.KtorStatus.NotFound
                ),
                status = HttpStatusCode.NotFound
            )
        }

        exception<RuntimeException> { call, e ->
            call.respond(
                message = AuthResponse(
                    code = Error.UnauthorizedError.code,
                    type = Error.UnauthorizedError::class.simpleName.toString(),
                    message = "Error: ${e.printStackTrace()}",
                    result = AuthResult.KtorStatus.Unauthorized
                ),
                status = HttpStatusCode.Unauthorized
            )
        }
    }
}

sealed class Error(val code: Int) {
    data object UnauthorizedError : Error(900)
    data object NotFoundError : Error(901)
}