package com.rvcoding.solotrek.ui.screens._internal.allinoneapi

import com.rvcoding.solotrek.data.local.UserSettings
import com.rvcoding.solotrek.data.local.Value
import com.rvcoding.solotrek.data.local.ksafe.KSafeWrapper
import com.rvcoding.solotrek.data.local.ksafe.mutableStateOf
import com.rvcoding.solotrek.domain.Result
import com.rvcoding.solotrek.domain.data.api.SoloTrekApi
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.FEATURE_FLAGS_KEY
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.SESSION_TTL_KEY
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.TIMEOUT_KEY
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.USER_ID_KEY
import com.rvcoding.solotrek.domain.data.repository.DataRepository
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
    private val api: SoloTrekApi,
    private val dataRepository: DataRepository,
    private val ksafe: KSafeWrapper,
    private val scope: CoroutineScope
) {

    private var counter by ksafe.mutableStateOf(0)

    private val _content = MutableStateFlow("Waiting for responses")
    fun setContent(value: String) { _content.update { value + counter++ } }
    val content: StateFlow<String> = _content.asStateFlow()

    val userConfig: StateFlow<UserSettings> = dataRepository
        .data
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = UserSettings(emptyMap())
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
                    dataRepository.update { config ->
                        FEATURE_FLAGS_KEY.let { config[it] = Value.StringVal(result.data.configuration.getOrElse(it) { "" }) }
                        TIMEOUT_KEY.let { config[it] = Value.StringVal(result.data.configuration.getOrElse(it) { "" }) }
                        SESSION_TTL_KEY.let { config[it] = Value.StringVal(result.data.configuration.getOrElse(it) { "" }) }
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
                    dataRepository.update { config ->
                        USER_ID_KEY.let { config[it] = Value.SecureStringVal(userId) }
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
                    dataRepository.update { config ->
                        USER_ID_KEY.let { config[it] = Value.SecureStringVal(userId) }
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
                    dataRepository.update { config ->
                        config.remove(USER_ID_KEY)
                    }
                    AllInOneApiState.Content.Logout
                }
                is Result.Error -> AllInOneApiState.Error(result.error.toString())
            }
        }
        _content.update { result.toString() }
    }
}