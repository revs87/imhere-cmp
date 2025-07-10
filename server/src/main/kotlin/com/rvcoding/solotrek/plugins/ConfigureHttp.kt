package com.rvcoding.solotrek.plugins

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS

fun Application.configureHttp() { // Or your main module function
    install(CORS) {
        // Allow requests from your WasmJS development server's origin
        allowHost("localhost:8081", schemes = listOf("http")
        ) // If WasmJS served from http://localhost:8081
        allowHost("127.0.0.1:8081", schemes = listOf("http"))// Also common for local dev

        // Or, more permissively for local development (less secure for production)
        // anyHost() // Allows all hosts - use with caution

        // Allow specific HTTP methods
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)

        // Allow specific headers
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader("Your-Custom-Header") // If you use custom headers

        // Allow credentials (if your WasmJS app sends cookies or auth headers)
        // allowCredentials = true

        // You can also use allowHeaders { true } or allowMethods { true } for more permissive settings
        // but explicit is often better.
    }
}