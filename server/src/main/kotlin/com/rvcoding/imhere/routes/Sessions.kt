package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.api.response.SessionsResponse
import com.rvcoding.imhere.domain.repository.SessionRepository
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get


fun Routing.sessions() {
    val sessionRepository: SessionRepository = get<SessionRepository>()

    get(Route.Sessions.path) {
        val sessions = sessionRepository.getAllSessions()
        call.respondText(Json.encodeToString(SessionsResponse(sessions)), ContentType.Application.Json)
    }

}