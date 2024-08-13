package com.rvcoding.imhere

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
actual fun getPlatformType(): PlatformType = PlatformType.SERVER(JVMPlatform().name)