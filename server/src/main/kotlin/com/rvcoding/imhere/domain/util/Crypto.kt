package com.rvcoding.imhere.domain.util

import org.kotlincrypto.hash.sha2.SHA256
import java.util.UUID


fun uniqueRandom(): String = UUID.randomUUID().toString()
fun String.sha256(): String = SHA256().digest(this.encodeToByteArray()).toHexString()
fun ByteArray.toHexString(): String = joinToString("") { "%02x".format(it) }
