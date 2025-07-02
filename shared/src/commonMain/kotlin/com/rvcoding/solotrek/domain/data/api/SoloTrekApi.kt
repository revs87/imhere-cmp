package com.rvcoding.solotrek.domain.data.api

import com.rvcoding.solotrek.PlatformType
import com.rvcoding.solotrek.domain.Result
import com.rvcoding.solotrek.domain.data.api.error.HttpError
import com.rvcoding.solotrek.domain.data.api.response.AuthResponse
import com.rvcoding.solotrek.domain.data.api.response.ConfigurationResponse
import com.rvcoding.solotrek.domain.data.api.response.SubscriptionsResponse
import com.rvcoding.solotrek.domain.data.api.response.UsersResponse
import com.rvcoding.solotrek.domain.model.User
import com.rvcoding.solotrek.getPlatformType

interface SoloTrekApi {
    suspend fun getConfiguration(): Result<ConfigurationResponse, HttpError>
    suspend fun register(userId: String, password: String, firstName: String, lastName: String): Result<AuthResponse, HttpError>
    suspend fun login(userId: String, password: String): Result<AuthResponse, HttpError>
    suspend fun logout(userId: String): Result<AuthResponse, HttpError>
    suspend fun users(): Result<UsersResponse, HttpError>
//    suspend fun sessions()
//    suspend fun subscriptions(): Result<SubscriptionsResponse, HttpError>
    suspend fun subscriptionsFromUser(userId: String): Result<SubscriptionsResponse, HttpError>
    suspend fun userSubscribedUsers(userId: String): Result<UsersResponse, HttpError>
    suspend fun userSubscribers(userId: String): Result<UsersResponse, HttpError>
    suspend fun subscribe(userId: String, userIdToSubscribe: String): Result<Unit, HttpError>
    suspend fun unsubscribe(userId: String, userIdToUnsubscribe: String): Result<Unit, HttpError>
    suspend fun state(userId: String): Result<User, HttpError>
    suspend fun state(user: User): Result<Unit, HttpError>
    suspend fun sync(user: User): Result<Unit, HttpError>
}

val URL = when (getPlatformType()) {
    is PlatformType.ANDROID -> "http://2.83.63.222:8080"
//    is PlatformType.ANDROID -> "http://192.168.1.170:8080"
    is PlatformType.DESKTOP -> "http://0.0.0.0:8080"
    is PlatformType.IOS -> "http://0.0.0.0:8080"
    is PlatformType.WEB -> "http://0.0.0.0:8080"
}