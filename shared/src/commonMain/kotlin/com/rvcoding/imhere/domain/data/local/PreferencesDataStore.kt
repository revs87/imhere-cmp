package com.rvcoding.imhere.domain.data.local

import kotlinx.coroutines.flow.Flow

typealias Key = String
sealed interface Value {
    data class StringValue(val value: String) : Value
    data class IntValue(val value: Int) : Value
    data class LongValue(val value: Long) : Value
}

interface PreferencesDataStore {
    fun preferences(): Flow<Map<Key, Value>>
    fun update(key: Key, value: Value)
}