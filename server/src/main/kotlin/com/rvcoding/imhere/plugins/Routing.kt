package com.rvcoding.imhere.plugins

import com.rvcoding.imhere.routes.authentication
import com.rvcoding.imhere.routes.configuration
import com.rvcoding.imhere.routes.root
import com.rvcoding.imhere.routes.sessions
import com.rvcoding.imhere.routes.subscriptions
import com.rvcoding.imhere.routes.users
import io.ktor.server.application.Application
import io.ktor.server.routing.routing


fun Application.configureRouting() {
    routing {
        root()
        configuration()
        authentication()
        users()
        sessions()
        subscriptions()
    }
}

