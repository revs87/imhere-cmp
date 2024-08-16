package com.rvcoding.imhere.plugins

import com.rvcoding.imhere.domain.response.AuthResponse
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
                    Error.NotFoundError.code,
                    Error.NotFoundError::class.simpleName.toString(),
                    "Not found."
                ),
                status = HttpStatusCode.NotFound
            )
        }

        exception<RuntimeException> { call, _ ->
            call.respond(
                message = AuthResponse(
                    Error.UnauthorizedError.code,
                    Error.UnauthorizedError::class.simpleName.toString(),
                    "Unauthorized."
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