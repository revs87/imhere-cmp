package com.rvcoding.imhere.data.remote.engine

import io.ktor.client.engine.HttpClientEngineFactory

expect fun platformHttpClientEngineFactory(): HttpClientEngineFactory<*>