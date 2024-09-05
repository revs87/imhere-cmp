package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.api.error.HttpError
import com.rvcoding.imhere.domain.data.api.response.UsersResponse
import com.rvcoding.imhere.domain.data.repository.UsersRepositoryApiOnlyImpl

actual class UsersRepositoryPlatformImpl actual constructor(private val api: IHApi) : UsersRepositoryApiOnlyImpl(api) {

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