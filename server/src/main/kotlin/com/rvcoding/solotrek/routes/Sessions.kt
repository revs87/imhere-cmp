package com.rvcoding.solotrek.routes

import com.rvcoding.solotrek.domain.Route
import com.rvcoding.solotrek.domain.data.api.response.ApiResponse
import com.rvcoding.solotrek.domain.data.api.response.ApiResult.Failure
import com.rvcoding.solotrek.domain.data.api.response.SessionsResponse
import com.rvcoding.solotrek.domain.repository.ApiSessionRepository
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun Routing.sessions() {
    val sessionRepository: ApiSessionRepository by com.rvcoding.solotrek.inject<ApiSessionRepository>()

    get(Route.Sessions.endpoint) {
        try {
            val sessions = sessionRepository.getAllSessions()
            call.respondText(Json.encodeToString(SessionsResponse(sessions)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(
                text = Json.encodeToString(ApiResponse(Failure("${e.message}"))),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

}
