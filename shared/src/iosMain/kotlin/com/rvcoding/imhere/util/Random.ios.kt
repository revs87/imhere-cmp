package com.rvcoding.imhere.util

import platform.Foundation.NSUUID

actual fun randomUUID(): UUID = iOSUUID(NSUUID().UUIDString())

data class iOSUUID(override val uuid: String) : UUID