package com.rvcoding.imhere.domain

class Configuration {
    companion object {
        val COMPANY_ID: String by lazy { "rvc-imhere" }

        const val TIMEOUT: Long = 60000L
    }
}