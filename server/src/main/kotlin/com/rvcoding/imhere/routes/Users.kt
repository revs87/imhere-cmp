package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.request.UserCoordinatesRequest
import com.rvcoding.imhere.domain.data.api.request.UserStateRequest
import com.rvcoding.imhere.domain.data.api.response.UserStateResponse
import com.rvcoding.imhere.domain.data.api.response.UsersResponse
import com.rvcoding.imhere.domain.data.db.toExposed
import com.rvcoding.imhere.domain.model.Coordinates
import com.rvcoding.imhere.domain.repository.ApiUserRepository
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject


fun Routing.users() {
    val userRepository: ApiUserRepository by inject<ApiUserRepository>()

    get(Route.Users.endpoint) {
        try {
            val users = userRepository.getAll()
            call.respondText(
                text = Json.encodeToString(UsersResponse(users.map { it.toExposed() })),
                contentType = ContentType.Application.Json
            )
        } catch (e: Exception) {
            call.respondText(
                text = Json.encodeToString(UsersResponse.empty()),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    get(Route.State.endpoint) {
        try {
            val userId = call.request.queryParameters["userId"] ?: ""
            userRepository.get(userId)?.let { user ->
                call.respondText(Json.encodeToString(UserStateResponse(user.toExposed())), ContentType.Application.Json)
            } ?: call.respondText(
                text = Json.encodeToString(UserStateResponse(null)),
                contentType = ContentType.Application.Json
            )
        } catch (e: Exception) {
            call.respondText(
                text = Json.encodeToString(UserStateResponse(null)),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    post(Route.State.endpoint) {
        try {
            val request = call.receive<UserStateRequest>()
            userRepository.get(request.userId)?.let { user ->
                userRepository.updateLastActivity(user.id)
                userRepository.updateState(user.id, request.state)
                call.respondText(Json.encodeToString(UserStateResponse(user.copy(state = request.state.state).toExposed())), ContentType.Application.Json)
            } ?: call.respondText(
                text = Json.encodeToString(UserStateResponse(null)),
                contentType = ContentType.Application.Json
            )
        } catch (e: Exception) {
            call.respondText(
                text = Json.encodeToString(UserStateResponse(null)),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    post(Route.Sync.endpoint) {
        try {
            val request = call.receive<UserCoordinatesRequest>()
            userRepository.get(request.userId)?.let { user ->
                userRepository.updateLastActivity(user.id)
                val coordinates = Coordinates(request.lat, request.lon, request.timestamp ?: System.currentTimeMillis())
                userRepository.updateCoordinates(request.userId, coordinates)
                call.respondText(Json.encodeToString(
                    UserStateResponse(user.copy(
                    lat = coordinates.lat,
                    lon = coordinates.lon,
                    lastCoordinatesTimestamp = coordinates.timestamp
                ).toExposed())
                ), ContentType.Application.Json)
            } ?: call.respondText(
                text = Json.encodeToString(UserStateResponse(null)),
                contentType = ContentType.Application.Json
            )
        } catch (e: Exception) {
            call.respondText(
                text = Json.encodeToString(UserStateResponse(null)),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}