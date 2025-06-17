package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import io.ktor.http.HttpStatusCode
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get


fun Routing.root() {
    staticResources("/rvcbanner", "static") { default("rvcbanner.jpg") }
    staticResources("/styles", "styles") { default("styles.css") }

    get(Route.Root.endpoint) {
        call.respond(
            message = "Hello, Kotlin Multiplatform guinea pig!",
            status = HttpStatusCode.OK
        )
    }
}