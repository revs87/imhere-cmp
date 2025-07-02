package com.rvcoding.solotrek.ui.screens.users

import com.rvcoding.solotrek.domain.Result
import com.rvcoding.solotrek.domain.model.User
import com.rvcoding.solotrek.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersStateModel(
    private val usersRepository: UsersRepository,
    private val coScope: CoroutineScope
) {
    val error: Channel<String> = Channel(Channel.UNLIMITED)

    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    fun requestUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.users(userId)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> _users.update { usersRepository.users.value }
        }
    }
    fun getUserSubscriptions(userId: String) = coScope.launch {
        when (val result = usersRepository.userSubscriptions(userId)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> {}
        }
    }
    fun requestSubscribedUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.usersSubscribed(userId)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> _users.update { usersRepository.subscribedUsers.value }
        }
    }
    fun requestSubscribingUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.usersSubscribing(userId)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> _users.update { usersRepository.subscribingUsers.value }
        }
    }
    fun requestSubscription(userId: String, userIdToSubscribe: String) = coScope.launch {
        when (val result = usersRepository.subscribe(userId, userIdToSubscribe)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> _users.update { usersRepository.subscribedUsers.value }
        }
    }
    fun requestUnsubscription(userId: String, userIdToUnsubscribe: String) = coScope.launch {
        when (val result = usersRepository.unsubscribe(userId, userIdToUnsubscribe)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> _users.update { usersRepository.subscribedUsers.value }
        }
    }
}