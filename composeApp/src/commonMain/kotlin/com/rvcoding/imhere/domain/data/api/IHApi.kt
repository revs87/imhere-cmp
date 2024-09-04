package com.rvcoding.imhere.domain.data.api

import com.rvcoding.imhere.PlatformType
import com.rvcoding.imhere.api.response.UsersResponse
import com.rvcoding.imhere.domain.HttpError
import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.getPlatformType

interface IHApi {
    suspend fun getConfiguration()
    suspend fun register()
    suspend fun login()
    suspend fun users(): Result<UsersResponse, HttpError>
    suspend fun sessions()
    suspend fun subscriptions()
    suspend fun subscriptionsFromUser(userId: String)
    suspend fun subscribersOfUser(userId: String)
    suspend fun subscribe()
    suspend fun unsubscribe()
    suspend fun state(userId: String)
    suspend fun state()
    suspend fun sync()

    companion object {
        internal val URL = when (getPlatformType()) {
            is PlatformType.ANDROID -> "http://10.0.2.2:8080/"
            is PlatformType.DESKTOP -> "http://0.0.0.0:8080/"
            is PlatformType.IOS -> "http://0.0.0.0:8080/"
            is PlatformType.WEB -> "http://0.0.0.0:8080/"
        }
    }
}
