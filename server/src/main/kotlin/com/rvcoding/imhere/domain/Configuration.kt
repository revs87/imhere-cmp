package com.rvcoding.imhere.domain

import kotlin.time.Duration.Companion.days

class Configuration {
    companion object {
        val APP_ID: String by lazy { "rvc-imhere" }
        const val TIMEOUT: Long = 60000L
        val SESSION_TTL: Long = 90.days.inWholeMilliseconds
    }
}