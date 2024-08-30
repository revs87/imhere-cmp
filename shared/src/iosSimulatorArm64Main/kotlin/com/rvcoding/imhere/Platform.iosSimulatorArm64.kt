package com.rvcoding.imhere

import platform.UIKit.UIDevice

class IOSSimulatorPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSSimulatorPlatform()
actual fun getPlatformType(): PlatformType = PlatformType.IOS(IOSSimulatorPlatform().name)