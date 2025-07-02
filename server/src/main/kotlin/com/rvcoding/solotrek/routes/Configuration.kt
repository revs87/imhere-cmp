package com.rvcoding.solotrek.routes

import com.rvcoding.solotrek.domain.Configuration.Companion.SESSION_TTL
import com.rvcoding.solotrek.domain.Configuration.Companion.TIMEOUT
import com.rvcoding.solotrek.domain.Route
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.FEATURE_FLAGS_KEY
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.SESSION_TTL_KEY
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.TIMEOUT_KEY
import com.rvcoding.solotrek.domain.data.api.response.ConfigurationResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.configuration() {

    get(Route.Configuration.endpoint) {
        call.respond(
            message = ConfigurationResponse(
                configuration = mapOf(
                    FEATURE_FLAGS_KEY to "",
                    TIMEOUT_KEY to TIMEOUT.toString(),
                    SESSION_TTL_KEY to SESSION_TTL.toString(),
                )
            ),
            status = HttpStatusCode.OK
        )
    }
}
