package com.rvcoding.solotrek.domain.util

import org.kotlincrypto.hash.sha2.SHA256


fun String.sha256(): String = SHA256().digest(this.encodeToByteArray()).toHexString()
fun ByteArray.toHexString(): String = joinToString("") { "%02x".format(it) }
