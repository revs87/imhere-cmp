package com.rvcoding.solotrek.di

import com.rvcoding.solotrek.data.api.SoloTrekService
import com.rvcoding.solotrek.data.location.LocationProviderFactory
import com.rvcoding.solotrek.data.permissions.PermissionHandlerFactory
import com.rvcoding.solotrek.data.remote.engine.platformHttpClientEngineFactory
import com.rvcoding.solotrek.data.repository.DataRepositoryImpl
import com.rvcoding.solotrek.domain.data.api.SoloTrekApi
import com.rvcoding.solotrek.domain.data.repository.DataRepository
import com.rvcoding.solotrek.domain.repository.UsersRepository
import com.rvcoding.solotrek.domain.repository.UsersRepositoryPlatformImpl
import com.rvcoding.solotrek.ui.navigation.NavigationGraphStateModel
import com.rvcoding.solotrek.ui.navigation.core.DefaultNavigator
import com.rvcoding.solotrek.ui.navigation.core.Navigator
import com.rvcoding.solotrek.ui.screens.poc.allinoneapi.AllInOneApiStateModel
import com.rvcoding.solotrek.ui.screens.poc.location.LocationStateModel
import com.rvcoding.solotrek.ui.screens.poc.maps.MapsStateModel
import com.rvcoding.solotrek.ui.screens.poc.users.UsersStateModel
import com.rvcoding.solotrek.util.DispatchersProvider
import com.rvcoding.solotrek.util.StandardDispatchersProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single<DispatchersProvider> { StandardDispatchersProvider }
    single { CoroutineScope(StandardDispatchersProvider.io) }

    /** API Client */
    // TODO https://ktor.io/docs/client-engines.html#mpp-config
    fun provideHttpClient(): HttpClient = HttpClient(platformHttpClientEngineFactory()) {
        // engine {} // config CIO -> https://ktor.io/docs/client-engines.html#jvm-android-native
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(HttpCallValidator)
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000L
            connectTimeoutMillis = 30_000L
            socketTimeoutMillis = 30_000L
        }
        install(HttpRedirect) {
            this.checkHttpMethod = false
        }
    }
        .also {
            it.plugin(HttpSend).intercept { request ->
                println("Intercepting request: ${request.url}")
                //request.headers.append("X-Custom-Header", "MyValue")
                val call = execute(request)
                println("Response received: ${call.response.status}")
                call
            }
        }
    single { provideHttpClient() }
    single<SoloTrekApi> { SoloTrekService(get()) }

    /** Repositories */
    single<UsersRepository> { UsersRepositoryPlatformImpl(get()) }
    single<DataRepository> { DataRepositoryImpl() }

    /** StateModels */
    factory { UsersStateModel(get(), get()) }
    factory { AllInOneApiStateModel(get(), get(), get(), get()) }

    /** Location */
    factory { LocationStateModel(get(), get(), get()) }
    factory { MapsStateModel(get(), get(), get()) }
    factory { PermissionHandlerFactory() }
    factory { LocationProviderFactory() }

    /* Navigation */
    single<Navigator> { DefaultNavigator(NavigationGraphStateModel.INITIAL_DESTINATION) }
    factory { NavigationGraphStateModel(get(), get()) }
}