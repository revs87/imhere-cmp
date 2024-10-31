package com.rvcoding.imhere.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.rvcoding.imhere.data.api.IHService
import com.rvcoding.imhere.data.local.DataStoreFactoryImpl
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.repository.UsersRepository
import com.rvcoding.imhere.domain.repository.UsersRepositoryPlatformImpl
import com.rvcoding.imhere.ui.screens.allinoneapi.AllInOneApiStateModel
import com.rvcoding.imhere.ui.screens.users.UsersStateModel
import com.rvcoding.imhere.util.StandardDispatchersProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single { StandardDispatchersProvider }
    single { CoroutineScope(StandardDispatchersProvider.io) }

    /** API Client */
    fun provideHttpClient(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true})
        }
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
        install(HttpRedirect) {
            this.checkHttpMethod = false
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

    /** Key-Value Storage */
    single<DataStore<Preferences>> { DataStoreFactoryImpl().dataStore() }

    /** Repositories */
    single<UsersRepository> { UsersRepositoryPlatformImpl(get()) }

    /** StateModels */
    factory { UsersStateModel(get(), get()) }
    factory { AllInOneApiStateModel(get(), get(), get()) }
}