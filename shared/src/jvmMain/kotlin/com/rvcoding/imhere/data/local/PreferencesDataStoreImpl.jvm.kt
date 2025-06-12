package com.rvcoding.imhere.data.local

import com.rvcoding.imhere.domain.data.local.Key
import com.rvcoding.imhere.domain.data.local.PreferencesDataStore
import com.rvcoding.imhere.domain.data.local.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class PreferencesDataStoreImpl : PreferencesDataStore {
    override fun preferences(): Flow<Map<Key, Value>> = flow {
        emit(emptyMap()) //TODO
    }

    override fun update(key: Key, value: Value) {
    }
}