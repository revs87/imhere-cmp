package com.rvcoding.solotrek

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
actual fun getPlatformType(): PlatformType = PlatformType.WEB(WasmPlatform().name)