package com.rvcoding.solotrek.domain.data.repository

import com.rvcoding.solotrek.data.local.UserSettings
import com.rvcoding.solotrek.data.local.Value
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    val data: Flow<UserSettings>

    fun update(config: (MutableMap<String, Value>) -> Unit)
}