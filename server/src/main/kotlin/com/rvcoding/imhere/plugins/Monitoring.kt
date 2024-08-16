package com.rvcoding.imhere.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging

//import io.ktor.server.plugins.calllogging.CallLogging

fun Application.configureMonitoring() {
    install(CallLogging)
}
