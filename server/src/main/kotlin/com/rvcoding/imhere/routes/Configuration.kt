package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Configuration.Companion.TIMEOUT
import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.routes.ConfigurationKeys.Companion.FEATURE_FLAGS_KEY
import com.rvcoding.imhere.routes.ConfigurationKeys.Companion.TIMEOUT_KEY
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable

fun Routing.configuration() {

    get(Route.Configuration.path) {
        call.respond(
            message = ConfigurationResponse(
                configuration = mapOf(
                    FEATURE_FLAGS_KEY to "",
                    TIMEOUT_KEY to TIMEOUT.toString(),
                )
            ),
            status = HttpStatusCode.OK
        )
    }
}

@Serializable
data class ConfigurationResponse(
    val configuration: Map<String, String>
)

class ConfigurationKeys {
    companion object {
        const val FEATURE_FLAGS_KEY = "featureFlags"
        const val TIMEOUT_KEY = "timeoutInMillis"
    }
}