package com.rvcoding.imhere.util

actual fun randomUUID(): UUID = JvmUUID(java.util.UUID.randomUUID().toString())

data class JvmUUID(override val uuid: String) : UUID