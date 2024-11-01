package com.rvcoding.imhere.ui.screens.allinoneapi

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.api.model.ConfigurationKeys.Companion.FEATURE_FLAGS_KEY
import com.rvcoding.imhere.domain.data.api.model.ConfigurationKeys.Companion.SESSION_TTL_KEY
import com.rvcoding.imhere.domain.data.api.model.ConfigurationKeys.Companion.TIMEOUT_KEY
import com.rvcoding.imhere.domain.data.api.model.ConfigurationKeys.Companion.USER_ID_KEY
import com.rvcoding.imhere.domain.data.api.model.ConfigurationKeys.Companion.asPreferenceKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AllInOneApiStateModel(
    private val api: IHApi,
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope
) {
    private val _content = MutableStateFlow("Waiting for responses")
    fun setContent(value: String) { _content.update { value } }
    val content: StateFlow<String> = _content.asStateFlow()

    val userConfig: StateFlow<Preferences?> = dataStore
        .data
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = null
        )

    val userIntent = Channel<AllInOneApiIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<AllInOneApiState>(AllInOneApiState.Initial)
    val state: StateFlow<AllInOneApiState> = _state.asStateFlow()
    init {
        userIntent
            .consumeAsFlow()
            .onEach { intent ->
                when (intent) {
                    is AllInOneApiIntent.Configuration -> requestConfiguration()
                    is AllInOneApiIntent.Register -> requestRegister(intent.userId, intent.password, intent.firstName, intent.lastName)
                    is AllInOneApiIntent.Login -> requestLogin(intent.userId, intent.password)
                    is AllInOneApiIntent.Logout -> requestLogout(intent.userId)
                }
            }
            .launchIn(scope)
    }

    private fun requestConfiguration() = scope.launch {
        _state.update { AllInOneApiState.Loading }
        val result = api.getConfiguration()
        _state.update {
            when (result) {
                is Result.Success -> {
                    dataStore.edit { config ->
                        FEATURE_FLAGS_KEY.asPreferenceKey().let { config[it] = result.data.configuration.getOrElse(it.name) { "" } }
                        TIMEOUT_KEY.asPreferenceKey().let { config[it] = result.data.configuration.getOrElse(it.name) { "" } }
                        SESSION_TTL_KEY.asPreferenceKey().let { config[it] = result.data.configuration.getOrElse(it.name) { "" } }
                    }
                    AllInOneApiState.Content.Configuration
                }
                is Result.Error -> AllInOneApiState.Error(result.error.toString())
            }
        }
        _content.update { result.toString() }
    }
    private fun requestRegister(
        userId: String,
        password: String,
        firstName: String,
        lastName: String
    ) = scope.launch {
        _state.update { AllInOneApiState.Loading }
        val result = api.register(
            userId = userId,
            password = password,
            firstName = firstName,
            lastName = lastName
        )
        _state.update {
            when (result) {
                is Result.Success -> {
                    dataStore.edit { config ->
                        config[USER_ID_KEY.asPreferenceKey()] = userId
                    }
                    AllInOneApiState.Content.Register
                }
                is Result.Error -> AllInOneApiState.Error(result.error.toString())
            }
        }
        _content.update { result.toString() }
    }
    private fun requestLogin(
        userId: String,
        password: String
    ) = scope.launch {
        _state.update { AllInOneApiState.Loading }
        val result = api.login(userId = userId, password = password)
        _state.update {
            when (result) {
                is Result.Success -> {
                    dataStore.edit { config ->
                        config[USER_ID_KEY.asPreferenceKey()] = userId
                    }
                    AllInOneApiState.Content.Login
                }
                is Result.Error -> AllInOneApiState.Error(result.error.toString())
            }
        }
        _content.update { result.toString() }
    }
    private fun requestLogout(userId: String) = scope.launch {
        _state.update { AllInOneApiState.Loading }
        val result = api.logout(userId = userId)
        _state.update {
            when (result) {
                is Result.Success -> {
                    dataStore.edit { config ->
                        config.remove(USER_ID_KEY.asPreferenceKey())
                    }
                    AllInOneApiState.Content.Logout
                }
                is Result.Error -> AllInOneApiState.Error(result.error.toString())
            }
        }
        _content.update { result.toString() }
    }
}