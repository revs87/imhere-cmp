package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.api.error.HttpError
import com.rvcoding.imhere.domain.data.api.response.UsersResponse
import com.rvcoding.imhere.domain.data.repository.UsersRepositoryApiOnlyImpl
import com.rvcoding.imhere.domain.model.User
import kotlinx.coroutines.flow.StateFlow


interface UsersRepository {
    val users : StateFlow<List<User>>
    val subscribedUsers : StateFlow<List<User>>
    val subscribingUsers : StateFlow<List<User>>

    suspend fun users(userId: String): Result<UsersResponse, HttpError>
    suspend fun usersSubscribed(userId: String): Result<UsersResponse, HttpError>
    suspend fun usersSubscribing(userId: String): Result<UsersResponse, HttpError>
    suspend fun subscribe(userId: String, userIdToSubscribe: String): Result<Unit, HttpError>
    suspend fun unsubscribe(userId: String, userIdToUnsubscribe: String): Result<Unit, HttpError>
}

expect class UsersRepositoryPlatformImpl(api: IHApi) : UsersRepositoryApiOnlyImpl
