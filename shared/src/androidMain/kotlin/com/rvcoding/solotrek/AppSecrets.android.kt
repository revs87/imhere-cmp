package com.rvcoding.solotrek

import com.rvcoding.solotrek.shared.BuildConfig

actual object AppSecrets {
    actual val mapsApiKey: String = BuildConfig.mapsApiKey
}