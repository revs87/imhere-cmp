package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.response.SessionsResponse
import com.rvcoding.imhere.domain.repository.ApiSessionRepository
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get


fun Routing.sessions() {
    val sessionRepository: ApiSessionRepository = get<ApiSessionRepository>()

    get(Route.Sessions.path) {
        val sessions = sessionRepository.getAllSessions()
        call.respondText(Json.encodeToString(SessionsResponse(sessions)), ContentType.Application.Json)
    }

}
