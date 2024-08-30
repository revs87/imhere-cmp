package com.rvcoding.imhere.util

import platform.Foundation.NSUUID

actual fun randomUUID(): UUID = iOSSimulatorUUID(NSUUID().UUIDString())

data class iOSSimulatorUUID(override val uuid: String) : UUID