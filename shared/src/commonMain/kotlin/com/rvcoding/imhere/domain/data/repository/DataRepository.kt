package com.rvcoding.imhere.domain.data.repository

import com.rvcoding.imhere.data.local.UserSettings
import com.rvcoding.imhere.data.local.Value
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    val data: Flow<UserSettings>

    fun update(config: (MutableMap<String, Value>) -> Unit)
}