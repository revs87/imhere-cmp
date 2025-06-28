package com.rvcoding.imhere.plugins

import com.rvcoding.imhere.di.initKoin
import com.rvcoding.imhere.di.serverModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(){
    initKoin(appModules = listOf(serverModule)) {
        slf4jLogger(level = org.koin.core.logger.Level.ERROR)
    }
    install(Koin){
        slf4jLogger(level = org.koin.core.logger.Level.ERROR)
        modules(serverModule)
    }
}