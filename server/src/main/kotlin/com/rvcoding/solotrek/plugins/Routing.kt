package com.rvcoding.solotrek.plugins

import com.rvcoding.solotrek.routes.authentication
import com.rvcoding.solotrek.routes.configuration
import com.rvcoding.solotrek.routes.root
import com.rvcoding.solotrek.routes.sessions
import com.rvcoding.solotrek.routes.subscriptions
import com.rvcoding.solotrek.routes.users
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

