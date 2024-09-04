package com.rvcoding.imhere.di

import com.rvcoding.imhere.data.api.IHService
import com.rvcoding.imhere.data.repository.UsersRepositoryImpl
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.repository.UsersRepository
import com.rvcoding.imhere.ui.screens.allinoneapi.AllInOneApiStateModel
import com.rvcoding.imhere.ui.screens.users.UsersStateModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val appModule = module {
    fun provideHttpClient(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
        //    install(Logging) {
        //        logger = Logger.DEFAULT
        //        level = LogLevel.ALL
        //    }
        install(HttpCallValidator)
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }
    }.also {
        it.plugin(HttpSend).intercept { request ->
            println("Intercepting request: ${request.url}")
            request.headers.append("X-Custom-Header", "MyValue")
            val call = execute(request)
            println("Response received: ${call.response.status}")
            call
        }
    }

    single { provideHttpClient() }
    single<IHApi> { IHService(get()) }
    single<UsersRepository> { UsersRepositoryImpl(get()) }

    factory { UsersStateModel(get()) }
    factory { AllInOneApiStateModel(get()) }
}