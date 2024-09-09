package com.rvcoding.imhere.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.rvcoding.imhere.domain.data.local.DataStoreFactory
import okio.Path.Companion.toPath


expect fun getDataStoreFilePath(): String

class DataStoreFactoryImpl : DataStoreFactory {
    override fun createDataStore(): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { getDataStoreFilePath().toPath() }
        )
}