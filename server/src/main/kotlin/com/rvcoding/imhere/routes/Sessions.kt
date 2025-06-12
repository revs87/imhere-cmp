package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.response.ApiResponse
import com.rvcoding.imhere.domain.data.api.response.ApiResult.Failure
import com.rvcoding.imhere.domain.data.api.response.SessionsResponse
import com.rvcoding.imhere.domain.repository.ApiSessionRepository
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject


fun Routing.sessions() {
    val sessionRepository: ApiSessionRepository by inject<ApiSessionRepository>()

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
