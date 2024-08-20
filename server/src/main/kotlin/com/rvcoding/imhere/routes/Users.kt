package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.repository.UserRepository
import com.rvcoding.imhere.domain.response.UsersResponse
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get


fun Routing.users() {
    val userRepository: UserRepository = get<UserRepository>()

    get(Route.Users.path) {
        val users = userRepository.getAll()
        call.respondText(Json.encodeToString(UsersResponse(users)), ContentType.Application.Json)
    }

}