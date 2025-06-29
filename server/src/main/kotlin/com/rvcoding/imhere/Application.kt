package com.rvcoding.imhere

import com.rvcoding.imhere.plugins.configureDefaultHeader
import com.rvcoding.imhere.plugins.configureKoin
import com.rvcoding.imhere.plugins.configureMonitoring
import com.rvcoding.imhere.plugins.configureRouting
import com.rvcoding.imhere.plugins.configureSerialization
import com.rvcoding.imhere.plugins.configureStatusPages
import io.ktor.server.application.Application
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier


/**
 * Fix for Koin `inject` for Ktor 3.0.3 using Koin 4.0.4 in Routes (e.g. in Routing.kt)
 * https://github.com/InsertKoinIO/koin/issues/1716#issuecomment-2888551504
 * */
inline fun <reified T : Any> inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) =
    lazy {
        GlobalContext.getKoinApplicationOrNull()?.koin?.get<T>(qualifier, parameters)
            ?: org.koin.java.KoinJavaComponent.inject<T>(T::class.java).value
    }


/**
 * In order to avoid Koin late readiness, here are the correct steps:
 * + Clean, Build, Build again and then Run, or
 * + Add another Build entry to Configuration->Before launch.
 * */
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