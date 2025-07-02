package com.rvcoding.solotrek.domain.repository

import com.rvcoding.solotrek.data.repository.UsersRepositoryApiOnlyImpl
import com.rvcoding.solotrek.domain.Result
import com.rvcoding.solotrek.domain.data.api.SoloTrekApi
import com.rvcoding.solotrek.domain.data.api.error.HttpError
import com.rvcoding.solotrek.domain.data.api.response.UsersResponse

actual class UsersRepositoryPlatformImpl actual constructor(private val api: SoloTrekApi) : UsersRepositoryApiOnlyImpl(api) {

    override suspend fun users(userId: String): Result<UsersResponse, HttpError> {
        return super.users(userId)
    }

    override suspend fun usersSubscribed(userId: String): Result<UsersResponse, HttpError> {
        return super.usersSubscribed(userId)
    }

    override suspend fun usersSubscribing(userId: String): Result<UsersResponse, HttpError> {
        return super.usersSubscribing(userId)
    }

    override suspend fun subscribe(userId: String, userIdToSubscribe: String): Result<Unit, HttpError> {
        return super.subscribe(userId, userIdToSubscribe)
    }

    override suspend fun unsubscribe(userId: String, userIdToUnsubscribe: String): Result<Unit, HttpError> {
        return super.unsubscribe(userId, userIdToUnsubscribe)
    }
}