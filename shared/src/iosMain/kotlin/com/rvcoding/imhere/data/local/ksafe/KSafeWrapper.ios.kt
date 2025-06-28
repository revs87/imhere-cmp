package com.rvcoding.imhere.data.local.ksafe

import eu.anifantakis.lib.ksafe.KSafe
import org.koin.mp.KoinPlatform

actual class KSafeWrapper {
    val ksafe: KSafe by KoinPlatform.getKoin().inject<KSafe>()

    actual inline fun <reified T> getDirect(key: String, defaultValue: T, encrypted: Boolean): T = ksafe.getDirect(key, defaultValue, encrypted)
    actual inline fun <reified T> putDirect(key: String, value: T, encrypted: Boolean) = ksafe.putDirect(key, value, encrypted)

    actual suspend inline fun <reified T> get(key: String, defaultValue: T, encrypted: Boolean): T = ksafe.get(key, defaultValue, encrypted)
    actual suspend inline fun <reified T> put(key: String, value: T, encrypted: Boolean) = ksafe.put(key, value, encrypted)

    actual suspend fun delete(key: String) = ksafe.delete(key)
    actual fun deleteDirect(key: String) = ksafe.deleteDirect(key)
}