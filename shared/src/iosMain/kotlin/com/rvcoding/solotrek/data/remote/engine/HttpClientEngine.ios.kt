package com.rvcoding.solotrek.data.remote.engine

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun platformHttpClientEngineFactory(): HttpClientEngineFactory<*> = Darwin