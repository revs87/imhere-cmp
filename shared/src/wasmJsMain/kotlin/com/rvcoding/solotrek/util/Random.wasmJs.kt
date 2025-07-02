package com.rvcoding.solotrek.util

import uuid

actual fun randomUUID(): UUID = WasmJsUUID(generateWasmJsUUID())

data class WasmJsUUID(override val uuid: String) : UUID

fun generateWasmJsUUID(): String = uuid.v4()
