package com.rvcoding.imhere

class Greeting {
    private val platform = getPlatform()
    private val platformType = getPlatformType()

    fun greet(): String {
        val platformId = platformType.id()
        val platformName = platform.name
        return "Hello, ${platformId}! (${platformName})"
    }
}