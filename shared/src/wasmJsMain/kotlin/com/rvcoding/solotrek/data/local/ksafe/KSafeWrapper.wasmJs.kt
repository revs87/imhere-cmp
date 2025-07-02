package com.rvcoding.solotrek.data.local.ksafe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// File: KSafeWrapper.wasmJs.kt ([1])
actual class KSafeWrapper {
    // For wasmJs, this map will live as long as the Wasm module instance.
    // It's not persistent across page reloads unless you explicitly save/load it
    // using browser APIs (like localStorage), which is outside the scope of a simple MutableMap.
    val storage: MutableMap<String, Any?> = mutableMapOf()
    // Note: For wasmJs, concurrency with a simple mutableMap is less of an issue than on JVM
    // unless you are using complex coroutine setups with multiple "threads" (workers, etc.).
    // For typical single-threaded WasmJs, a plain mutableMap is often sufficient.

    actual inline fun <reified T> getDirect(
        key: String,
        defaultValue: T,
        encrypted: Boolean // Ignored for wasmJs in-memory map
    ): T {
        val value = storage[key]
        return if (value is T) {
            value
        } else {
            defaultValue
        }
    }

    actual inline fun <reified T> putDirect(
        key: String,
        value: T,
        encrypted: Boolean // Ignored
    ) {
        storage[key] = value
    }

    actual suspend inline fun <reified T> get(
        key: String,
        defaultValue: T,
        encrypted: Boolean // Ignored
    ): T {
        // For wasmJs, using withContext might not be strictly necessary for a simple map access
        // as it's single-threaded by default for UI-related code.
        // However, Dispatchers.Default is a reasonable choice if you want to
        // maintain the suspend modifier idiomatically or represent a potentially
        // more complex operation in the future.
        return withContext(Dispatchers.Default) {
            val value = storage[key]
            if (value is T) {
                value
            } else {
                defaultValue
            }
        }
    }

    actual suspend inline fun <reified T> put(
        key: String,
        value: T,
        encrypted: Boolean // Ignored
    ) {
        withContext(Dispatchers.Default) {
            storage[key] = value
        }
    }

    actual suspend fun delete(key: String) {
        withContext(Dispatchers.Default) {
            storage.remove(key)
        }
    }

    actual fun deleteDirect(key: String) {
        storage.remove(key)
    }
}