package com.rvcoding.imhere.ui.screens.users

import com.rvcoding.imhere.domain.Result.Error
import com.rvcoding.imhere.domain.Result.Success
import com.rvcoding.imhere.domain.data.repository.UsersRepository
import com.rvcoding.imhere.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersStateModel(
    private val usersRepository: UsersRepository,
) {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.users().let { result ->
                when (result) {
                    is Success -> _users.update { result.data.users }
                    is Error -> println("Error fetching users: ${result.error}")
                }
            }
        }
    }
}