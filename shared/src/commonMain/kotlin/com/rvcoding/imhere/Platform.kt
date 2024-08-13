package com.rvcoding.imhere

interface Platform {
    val name: String
}
expect fun getPlatform(): Platform

sealed class PlatformType {
    data class ANDROID(val name: String) : PlatformType()
    data class IOS(val name: String) : PlatformType()
    data class WEB(val name: String) : PlatformType()
    data class SERVER(val name: String) : PlatformType()
}
expect fun getPlatformType(): PlatformType
fun PlatformType.id(): String = this::class.simpleName ?: "Unknown"