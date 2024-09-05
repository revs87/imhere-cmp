package com.rvcoding.imhere.ui.screens.users

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.model.User
import com.rvcoding.imhere.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersStateModel(
    private val usersRepository: UsersRepository,
    private val coScope: CoroutineScope
) {
    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    fun requestUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.users(userId)) {
            is Result.Error -> println("Error: ${result.error}")
            is Result.Success -> _users.update { usersRepository.users.value }
        }
    }
    fun requestSubscribedUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.usersSubscribed(userId)) {
            is Result.Error -> println("Error: ${result.error}")
            is Result.Success -> _users.update { usersRepository.subscribedUsers.value }
        }
    }
    fun requestSubscribingUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.usersSubscribing(userId)) {
            is Result.Error -> println("Error: ${result.error}")
            is Result.Success -> _users.update { usersRepository.subscribingUsers.value }
        }
    }
    fun requestSubscription(userId: String, userIdToSubscribe: String) = coScope.launch {
        when (val result = usersRepository.subscribe(userId, userIdToSubscribe)) {
            is Result.Error -> println("Error: ${result.error}")
            is Result.Success -> _users.update { usersRepository.subscribedUsers.value }
        }
    }
    fun requestUnsubscription(userId: String, userIdToUnsubscribe: String) = coScope.launch {
        when (val result = usersRepository.unsubscribe(userId, userIdToUnsubscribe)) {
            is Result.Error -> println("Error: ${result.error}")
            is Result.Success -> _users.update { usersRepository.subscribedUsers.value }
        }
    }
}