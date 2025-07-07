package com.rvcoding.solotrek

import com.rvcoding.solotrek.util.getStringResource

actual object AppSecrets {
    actual val mapsApiKey: String = getStringResource(
        filename = "Secrets",
        fileType = "plist",
        valueKey = "mapsApiKey"
    ).also {
        println("mapsApiKey=$it")
    } ?: "".also {
        println("Failed to load mapsApiKey")
    }
}