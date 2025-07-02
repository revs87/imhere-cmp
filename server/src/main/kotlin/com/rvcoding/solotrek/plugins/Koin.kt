package com.rvcoding.solotrek.plugins

import com.rvcoding.solotrek.di.serverModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        modules(serverModule)
        slf4jLogger(level = org.koin.core.logger.Level.ERROR)
    }
}