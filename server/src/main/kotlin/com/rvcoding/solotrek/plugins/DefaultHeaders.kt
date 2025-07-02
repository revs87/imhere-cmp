package com.rvcoding.solotrek.plugins

import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import java.time.Duration

fun Application.configureDefaultHeader() {
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
        header("X-App-Name", "Solotrek")
        header("X-App-Version", "1.0.0")
        header("X-App-Author", "RVCoding")
    val oneYearInSeconds = Duration.ofDays(365).seconds
        header(
            name = HttpHeaders.CacheControl,
            value = "public, max-age=$oneYearInSeconds, immutable"
        )
    }
}