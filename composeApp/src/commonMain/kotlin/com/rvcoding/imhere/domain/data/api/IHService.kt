package com.rvcoding.imhere.domain.data.api

import com.rvcoding.imhere.api.response.UsersResponse
import com.rvcoding.imhere.domain.HttpError
import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.IHApi.Companion.URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class IHService(
    private val client: HttpClient
) : IHApi {
    override suspend fun getConfiguration() {}
    override suspend fun register() {}
    override suspend fun login() {}
    override suspend fun users(): Result<UsersResponse, HttpError> {
        return try {
            val responseObject = client.get("$URL${Route.Users.path}")
            val response: UsersResponse = responseObject.body()
            if (responseObject.status.value in 200..299) Result.Success(data = response)
            else Result.Error(
                error = HttpError.Connection(
                    code = responseObject.status.value,
                    codeDescription = responseObject.status.description
                )
            )
        } catch (e: Exception) {
            Result.Error(error = HttpError.Unknown)
        }
    }
    override suspend fun sessions() {}
    override suspend fun subscriptions() {}
    override suspend fun subscriptionsFromUser(userId: String) {}
    override suspend fun subscribersOfUser(userId: String) {}
    override suspend fun subscribe() {}
    override suspend fun unsubscribe() {}
    override suspend fun state(userId: String) {}
    override suspend fun state() {}
    override suspend fun sync() {}
}
