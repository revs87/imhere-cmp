package com.rvcoding.solotrek.data.remote.engine

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js

actual fun platformHttpClientEngineFactory(): HttpClientEngineFactory<*> = Js