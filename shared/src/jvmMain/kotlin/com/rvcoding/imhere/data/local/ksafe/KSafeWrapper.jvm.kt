package com.rvcoding.imhere.data.local.ksafe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Collections

actual class KSafeWrapper {
    // Use a thread-safe map if you expect concurrent access from different threads.
    // For simplicity, a standard MutableMap is used here. For true thread safety
    // without external synchronization, consider ConcurrentHashMap or wrapping access.
    val storage: MutableMap<String, Any?> = Collections.synchronizedMap(mutableMapOf<String, Any?>())

    actual inline fun <reified T> getDirect(key: String, defaultValue: T, encrypted: Boolean): T {
        // 'encrypted' parameter is ignored in this JVM map-based implementation
        val value = storage[key]
        return if (value is T) {
            value
        } else {
            defaultValue
        }
    }

    actual inline fun <reified T> putDirect(key: String, value: T, encrypted: Boolean) {
        // 'encrypted' parameter is ignored
        storage[key] = value
    }

    actual suspend inline fun <reified T> get(key: String, defaultValue: T, encrypted: Boolean): T {
        // 'encrypted' parameter is ignored
        // For suspend functions, you might want to switch context if the map could block,
        // but for a simple in-memory map, it's usually fast enough.
        // However, to maintain the suspend modifier and be idiomatic:
        return withContext(Dispatchers.IO) { // Or Dispatchers.Default for CPU-bound fake work
            val value = storage[key]
            if (value is T) {
                value
            } else {
                defaultValue
            }
        }
    }

    actual suspend inline fun <reified T> put(key: String, value: T, encrypted: Boolean) {
        // 'encrypted' parameter is ignored
        withContext(Dispatchers.IO) { // Or Dispatchers.Default
            storage[key] = value
        }
    }

    actual suspend fun delete(key: String) {
        withContext(Dispatchers.IO) { // Or Dispatchers.Default
            storage.remove(key)
        }
    }

    actual fun deleteDirect(key: String) {
        storage.remove(key)
    }
}