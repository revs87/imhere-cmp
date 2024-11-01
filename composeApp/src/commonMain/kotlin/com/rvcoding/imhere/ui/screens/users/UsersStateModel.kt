package com.rvcoding.imhere.ui.screens.users

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.model.User
import com.rvcoding.imhere.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersStateModel(
    private val usersRepository: UsersRepository,
    private val coScope: CoroutineScope
) {
    val error: Channel<String> = Channel(Channel.UNLIMITED)

    val users: StateFlow<List<User>> = usersRepository.users

    fun requestUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.users(userId)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> {}
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
            is Result.Success -> {}
        }
    }
    fun requestSubscribingUsers(userId: String) = coScope.launch {
        when (val result = usersRepository.usersSubscribing(userId)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> {}
        }
    }
    fun requestSubscription(userId: String, userIdToSubscribe: String) = coScope.launch {
        when (val result = usersRepository.subscribe(userId, userIdToSubscribe)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> {}
        }
    }
    fun requestUnsubscription(userId: String, userIdToUnsubscribe: String) = coScope.launch {
        when (val result = usersRepository.unsubscribe(userId, userIdToUnsubscribe)) {
            is Result.Error -> {
                println("Error: ${result.error}")
                error.send(result.error.toString())
            }
            is Result.Success -> {}
        }
    }
}