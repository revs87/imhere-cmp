package com.rvcoding.imhere

import com.rvcoding.imhere.plugins.configureDefaultHeader
import com.rvcoding.imhere.plugins.configureKoin
import com.rvcoding.imhere.plugins.configureMonitoring
import com.rvcoding.imhere.plugins.configureRouting
import com.rvcoding.imhere.plugins.configureSerialization
import com.rvcoding.imhere.plugins.configureStatusPages
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
//fun main() {
//    embeddedServer(
//        factory = Netty,
//        port = SERVER_PORT,
//        host = "0.0.0.0",
//        module = Application::module
//    ).start(wait = true)
//}

@Suppress("Unused")
fun Application.module() {
    configureSerialization()
    configureKoin()
    configureRouting()
    configureMonitoring()
    configureDefaultHeader()
    configureStatusPages()
}