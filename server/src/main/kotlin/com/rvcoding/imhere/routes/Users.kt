package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.api.request.UserCoordinatesRequest
import com.rvcoding.imhere.domain.api.request.UserStateRequest
import com.rvcoding.imhere.domain.api.response.UserStateResponse
import com.rvcoding.imhere.domain.api.response.UsersResponse
import com.rvcoding.imhere.model.Coordinates
import com.rvcoding.imhere.domain.models.toExposed
import com.rvcoding.imhere.domain.repository.UserRepository
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get


fun Routing.users() {
    val userRepository: UserRepository = get<UserRepository>()

    get(Route.Users.path) {
        val users = userRepository.getAll()
        call.respondText(Json.encodeToString(UsersResponse(users.map { it.toExposed() })), ContentType.Application.Json)
    }

    get(Route.State.path) {
        try {
            val userId = call.request.queryParameters["userId"] ?: ""
            userRepository.get(userId)?.let { user ->
                call.respondText(Json.encodeToString(UserStateResponse(user.toExposed())), ContentType.Application.Json)
            } ?: call.respondText(Json.encodeToString(UserStateResponse(null)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(Json.encodeToString(UserStateResponse(null)), ContentType.Application.Json)
        }
    }

    post(Route.State.path) {
        try {
            val request = call.receive<UserStateRequest>()
            userRepository.get(request.userId)?.let { user ->
                userRepository.updateLastActivity(user.id)
                userRepository.updateState(user.id, request.state)
                call.respondText(Json.encodeToString(UserStateResponse(user.copy(state = request.state.state).toExposed())), ContentType.Application.Json)
            } ?: call.respondText(Json.encodeToString(UserStateResponse(null)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(Json.encodeToString(UserStateResponse(null)), ContentType.Application.Json)
        }
    }

    post(Route.Sync.path) {
        try {
            val request = call.receive<UserCoordinatesRequest>()
            userRepository.get(request.userId)?.let { user ->
                userRepository.updateLastActivity(user.id)
                val coordinates = Coordinates(request.lat, request.lon, request.timestamp ?: System.currentTimeMillis())
                userRepository.updateCoordinates(request.userId, coordinates)
                call.respondText(Json.encodeToString(UserStateResponse(user.copy(
                    lat = coordinates.lat,
                    lon = coordinates.lon,
                    lastCoordinatesTimestamp = coordinates.timestamp
                ).toExposed())), ContentType.Application.Json)
            } ?: call.respondText(Json.encodeToString(UserStateResponse(null)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(Json.encodeToString(UserStateResponse(null)), ContentType.Application.Json)
        }
    }
}