package com.rvcoding.solotrek.util

import platform.Foundation.NSUUID

actual fun randomUUID(): UUID = iOSUUID(NSUUID().UUIDString())

data class iOSUUID(override val uuid: String) : UUID