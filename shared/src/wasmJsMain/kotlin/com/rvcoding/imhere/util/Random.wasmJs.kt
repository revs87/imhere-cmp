package com.rvcoding.imhere.util

import uuid

actual fun randomUUID(): UUID = WasmJsUUID(generateWasmJsUUID())

data class WasmJsUUID(override val uuid: String) : UUID

fun generateWasmJsUUID() = uuid.v4().toString()
