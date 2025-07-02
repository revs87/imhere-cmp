package com.rvcoding.solotrek

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
actual fun getPlatformType(): PlatformType = PlatformType.DESKTOP(JVMPlatform().name)