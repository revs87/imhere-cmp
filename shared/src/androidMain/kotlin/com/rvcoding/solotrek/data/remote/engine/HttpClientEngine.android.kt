package com.rvcoding.solotrek.data.remote.engine

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

actual fun platformHttpClientEngineFactory(): HttpClientEngineFactory<*> = CIO