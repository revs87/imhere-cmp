package com.rvcoding.imhere.domain.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


interface DataStoreFactory {
    fun dataStore(): DataStore<Preferences>
}

internal const val dataStoreFileName = "imhere.preferences_pb"
