package com.rvcoding.imhere.ui.screens.allinoneapi

import com.rvcoding.imhere.domain.data.api.IHApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllInOneApiStateModel(
    private val api: IHApi,
    private val scope: CoroutineScope
) {
    private val _content = MutableStateFlow("Waiting for responses")
    val content: StateFlow<String> = _content.asStateFlow()

    fun requestConfiguration() = scope.launch { _content.update { api.getConfiguration().toString() } }
    fun requestRegister(
        userId: String,
        password: String,
        firstName: String,
        lastName: String
    ) = scope.launch {
            _content.update { api.register(
                userId = userId,
                password = password,
                firstName = firstName,
                lastName = lastName
            ).toString() }
        }
    fun requestLogin(
        userId: String,
        password: String
    ) = scope.launch {
            _content.update { api.login(
                userId = userId,
                password = password,
            ).toString() }
        }
    fun requestUsers() = scope.launch { _content.update { api.users().toString() } }
}