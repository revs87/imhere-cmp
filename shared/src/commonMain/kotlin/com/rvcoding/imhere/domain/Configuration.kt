package com.rvcoding.imhere.domain

import kotlin.time.Duration.Companion.days

class Configuration {
    companion object {
        const val APP_ID: String = "rvc-imhere"
        const val TIMEOUT: Long = 20000L
        val SESSION_TTL: Long = 90.days.inWholeMilliseconds
    }
}