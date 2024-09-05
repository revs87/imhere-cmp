package com.rvcoding.imhere.ui.screens.users

import com.rvcoding.imhere.domain.Result.Error
import com.rvcoding.imhere.domain.Result.Success
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.repository.UsersRepository
import com.rvcoding.imhere.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersStateModel(
    private val api: IHApi,
    private val usersRepository: UsersRepository,
    private val coScope: CoroutineScope
) {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        coScope.launch {
            usersRepository.users().let { result ->
                when (result) {
                    is Success -> _users.update { result.data.users }
                    is Error -> println("Error fetching users: ${result.error}")
                }
            }
            api.subscribersOfUser("revs").let { result ->
                when (result) {
                    is Success -> println("Subscribers: ${result.data}")
                    is Error -> println("Error fetching subscribers: ${result.error}")
                }
            }
        }
    }
}