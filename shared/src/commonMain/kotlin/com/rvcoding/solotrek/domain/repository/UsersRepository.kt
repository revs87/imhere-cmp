package com.rvcoding.solotrek.domain.repository

import com.rvcoding.solotrek.data.repository.UsersRepositoryApiOnlyImpl
import com.rvcoding.solotrek.domain.Result
import com.rvcoding.solotrek.domain.data.api.SoloTrekApi
import com.rvcoding.solotrek.domain.data.api.error.HttpError
import com.rvcoding.solotrek.domain.data.api.response.SubscriptionsResponse
import com.rvcoding.solotrek.domain.data.api.response.UsersResponse
import com.rvcoding.solotrek.domain.model.User
import kotlinx.coroutines.flow.StateFlow


interface UsersRepository {
    val users : StateFlow<List<User>>
    val subscribedUsers : StateFlow<List<User>>
    val subscribingUsers : StateFlow<List<User>>

    suspend fun users(userId: String): Result<UsersResponse, HttpError>
    suspend fun userSubscriptions(userId: String): Result<SubscriptionsResponse, HttpError>
    suspend fun usersSubscribed(userId: String): Result<UsersResponse, HttpError>
    suspend fun usersSubscribing(userId: String): Result<UsersResponse, HttpError>
    suspend fun subscribe(userId: String, userIdToSubscribe: String): Result<Unit, HttpError>
    suspend fun unsubscribe(userId: String, userIdToUnsubscribe: String): Result<Unit, HttpError>
}

expect class UsersRepositoryPlatformImpl(api: SoloTrekApi) : UsersRepositoryApiOnlyImpl
