package com.rvcoding.imhere.data.repository

import com.rvcoding.imhere.data.local.UserSettings
import com.rvcoding.imhere.data.local.Value
import com.rvcoding.imhere.domain.data.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val config: MutableStateFlow<MutableMap<String, Value>> = MutableStateFlow(mutableMapOf())
) : DataRepository {

    override val data: Flow<UserSettings> = config.map { UserSettings(it) }

    override fun update(config: (MutableMap<String, Value>) -> Unit) {
        config.invoke(this.config.value)
    }
}