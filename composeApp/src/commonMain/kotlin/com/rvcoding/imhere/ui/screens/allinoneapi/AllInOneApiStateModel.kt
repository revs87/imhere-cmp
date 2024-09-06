package com.rvcoding.imhere.ui.screens.allinoneapi

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.data.api.IHApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AllInOneApiStateModel(
    private val api: IHApi,
    private val scope: CoroutineScope
) {
    private val _content = MutableStateFlow("Waiting for responses")
    val content: StateFlow<String> = _content.asStateFlow()

    val userIntent = Channel<AllInOneApiIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<AllInOneApiState>(AllInOneApiState.Initial)
    val state: StateFlow<AllInOneApiState> = _state.asStateFlow()
    init {
        userIntent.consumeAsFlow()
            .onEach { intent ->
                when (intent) {
                    is AllInOneApiIntent.Configuration -> requestConfiguration()
                    is AllInOneApiIntent.Register -> requestRegister(intent.userId, intent.password, intent.firstName, intent.lastName)
                    is AllInOneApiIntent.Login -> requestLogin(intent.userId, intent.password)
                }
            }
            .launchIn(scope)
    }

    private fun requestConfiguration() = scope.launch {
        _state.update { AllInOneApiState.Loading }
        val result = api.getConfiguration()
        _state.update {
            when (result) {
                is Result.Success -> AllInOneApiState.Content
                is Result.Error -> AllInOneApiState.Error
            }
        }
        _content.update { result.toString() }
    }
    fun requestRegister(
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
                is Result.Success -> AllInOneApiState.Content
                is Result.Error -> AllInOneApiState.Error
            }
        }
        _content.update { result.toString() }
    }
    fun requestLogin(
        userId: String,
        password: String
    ) = scope.launch {
        _state.update { AllInOneApiState.Loading }
        val result = api.login(userId = userId, password = password)
        _state.update {
            when (result) {
                is Result.Success -> AllInOneApiState.Content
                is Result.Error -> AllInOneApiState.Error
            }
        }
        _content.update { result.toString() }
    }
}