package com.rvcoding.solotrek.domain

import kotlin.time.Duration.Companion.days

class Configuration {
    companion object {
        const val APP_ID: String = "rvc-solotrek"
        const val TIMEOUT: Long = 20_000L
        val SESSION_TTL: Long = 90.days.inWholeMilliseconds
    }
}