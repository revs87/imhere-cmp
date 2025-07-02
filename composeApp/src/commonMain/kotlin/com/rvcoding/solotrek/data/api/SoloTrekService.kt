package com.rvcoding.solotrek.data.api

import com.rvcoding.solotrek.domain.Result
import com.rvcoding.solotrek.domain.Route
import com.rvcoding.solotrek.domain.data.api.AuthResult.LoginResult
import com.rvcoding.solotrek.domain.data.api.AuthResult.LogoutResult
import com.rvcoding.solotrek.domain.data.api.AuthResult.RegisterResult
import com.rvcoding.solotrek.domain.data.api.SoloTrekApi
import com.rvcoding.solotrek.domain.data.api.URL
import com.rvcoding.solotrek.domain.data.api.error.HttpError
import com.rvcoding.solotrek.domain.data.api.request.LoginRequest
import com.rvcoding.solotrek.domain.data.api.request.LogoutRequest
import com.rvcoding.solotrek.domain.data.api.request.RegisterRequest
import com.rvcoding.solotrek.domain.data.api.request.SubscribeRequest
import com.rvcoding.solotrek.domain.data.api.request.UnsubscribeRequest
import com.rvcoding.solotrek.domain.data.api.response.AuthResponse
import com.rvcoding.solotrek.domain.data.api.response.ConfigurationResponse
import com.rvcoding.solotrek.domain.data.api.response.SubscriptionsResponse
import com.rvcoding.solotrek.domain.data.api.response.UsersResponse
import com.rvcoding.solotrek.domain.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SoloTrekService(
    private val client: HttpClient
) : SoloTrekApi {
    override suspend fun getConfiguration(): Result<ConfigurationResponse, HttpError> {
        return try {
            val responseObject = client.get("$URL${Route.Configuration.endpoint}")
            val response: ConfigurationResponse = responseObject.body()
            when {
                responseObject.statusSuccess() -> Result.Success(data = response)
                else -> Result.Error(
                    error = HttpError.Communication(
                        code = responseObject.status.value,
                        codeDescription = responseObject.status.description
                    )
                )
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            Result.Error(error = HttpError.Unknown(message = e.message.toString()))
        }
    }
    override suspend fun register(userId: String, password: String, firstName: String, lastName: String): Result<AuthResponse, HttpError> {
        return try {
            val responseObject = client.post("$URL${Route.Register.endpoint}") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(userId = userId, password = password, firstName = firstName, lastName = lastName))
            }
            val response: AuthResponse = responseObject.body()
            when (response.result) {
                is RegisterResult.Success -> Result.Success(response)
                is RegisterResult.InvalidParametersError -> Result.Error(HttpError.Auth(response))
                is RegisterResult.CriteriaNotMetError -> Result.Error(HttpError.Auth(response))
                is RegisterResult.UserAlreadyRegisteredError -> Result.Error(HttpError.Auth(response))
                is RegisterResult.UnauthorizedError -> Result.Error(HttpError.Auth(response))
                // returned if user already registered (redirect to login)
                is LoginResult.Success -> Result.Success(response)
                is LoginResult.InvalidParametersError -> Result.Error(HttpError.Auth(response))
                is LoginResult.CredentialsMismatchError -> Result.Error(HttpError.Auth(response))
                is LoginResult.UnauthorizedError -> Result.Error(HttpError.Auth(response))
                else -> Result.Error(
                    error = HttpError.Communication(
                        code = responseObject.status.value,
                        codeDescription = responseObject.status.description
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(error = HttpError.Unknown(message = e.message.toString()))
        }
    }
    override suspend fun login(userId: String, password: String): Result<AuthResponse, HttpError> {
        return try {
            val responseObject = client.post("$URL${Route.Login.endpoint}") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(userId = userId, password = password))
            }
            val response: AuthResponse = responseObject.body()
            when (response.result) {
                is LoginResult.Success -> Result.Success(response)
                is LoginResult.InvalidParametersError -> Result.Error(HttpError.Auth(response))
                is LoginResult.CredentialsMismatchError -> Result.Error(HttpError.Auth(response))
                is LoginResult.UnauthorizedError -> Result.Error(HttpError.Auth(response))
                else -> Result.Error(
                    error = HttpError.Communication(
                        code = responseObject.status.value,
                        codeDescription = responseObject.status.description
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(error = HttpError.Unknown(message = e.message.toString()))
        }
    }

    override suspend fun logout(userId: String): Result<AuthResponse, HttpError> {
        return try {
            val responseObject = client.post("$URL${Route.Logout.endpoint}") {
                contentType(ContentType.Application.Json)
                setBody(LogoutRequest(userId = userId))
            }
            val response: AuthResponse = responseObject.body()
            when (response.result) {
                is LogoutResult.Success -> Result.Success(response)
                is LogoutResult.InvalidParametersError -> Result.Error(HttpError.Auth(response))
                is LogoutResult.UnauthorizedError -> Result.Error(HttpError.Auth(response))
                else -> Result.Error(
                    error = HttpError.Communication(
                        code = responseObject.status.value,
                        codeDescription = responseObject.status.description
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(error = HttpError.Unknown(message = e.message.toString()))
        }
    }

    override suspend fun subscriptionsFromUser(userId: String): Result<SubscriptionsResponse, HttpError> = try {
        val responseObject = client.get("$URL${Route.UserSubscriptions.endpoint}/?userId=$userId")
        val response: SubscriptionsResponse = responseObject.body()
        when {
            responseObject.statusSuccess() -> Result.Success(data = response)
            else -> Result.Error(
                error = HttpError.Communication(
                    code = responseObject.status.value,
                    codeDescription = responseObject.status.description
                )
            )
        }
    } catch (e: Exception) {
        Result.Error(error = HttpError.Unknown(message = e.message.toString()))
    }

    override suspend fun users(): Result<UsersResponse, HttpError> =
        usersResponseHttpRequest(endpoint = Route.Users.endpoint, userId = "")

    override suspend fun userSubscribedUsers(userId: String): Result<UsersResponse, HttpError> =
        usersResponseHttpRequest(endpoint = Route.UserSubscribedUsers.endpoint, userId = userId)

    override suspend fun userSubscribers(userId: String): Result<UsersResponse, HttpError> =
        usersResponseHttpRequest(endpoint = Route.UserSubscribers.endpoint, userId = userId)

    override suspend fun subscribe(userId: String, userIdToSubscribe: String): Result<Unit, HttpError> = try {
        val responseObject = client.post("$URL${Route.Subscribe.endpoint}") {
            contentType(ContentType.Application.Json)
            setBody(SubscribeRequest(userId = userId, userIdToSubscribe = userIdToSubscribe))
        }
        val response: Unit = responseObject.body()
        when {
            responseObject.statusSuccess() -> Result.Success(data = response)
            else -> Result.Error(
                error = HttpError.Communication(
                    code = responseObject.status.value,
                    codeDescription = responseObject.status.description
                )
            )
        }
    } catch (e: Exception) {
        Result.Error(error = HttpError.Unknown(message = e.message.toString()))
    }

    override suspend fun unsubscribe(userId: String, userIdToUnsubscribe: String): Result<Unit, HttpError> = try {
        val responseObject = client.post("$URL${Route.Unsubscribe.endpoint}") {
            contentType(ContentType.Application.Json)
            setBody(UnsubscribeRequest(userId = userId, userIdToUnsubscribe = userIdToUnsubscribe))
        }
        val response: Unit = responseObject.body()
        when {
            responseObject.statusSuccess() -> Result.Success(data = response)
            else -> Result.Error(
                error = HttpError.Communication(
                    code = responseObject.status.value,
                    codeDescription = responseObject.status.description
                )
            )
        }
    } catch (e: Exception) {
        Result.Error(error = HttpError.Unknown(message = e.message.toString()))
    }

    override suspend fun state(userId: String): Result<User, HttpError> { return Result.Error(notImplemented) }
    override suspend fun state(user: User): Result<Unit, HttpError> { return Result.Error(notImplemented) }
    override suspend fun sync(user: User): Result<Unit, HttpError> { return Result.Error(notImplemented) }

    private val notImplemented = HttpError.Unknown(message = "Not implemented")

    private suspend fun usersResponseHttpRequest(endpoint: String, userId: String): Result<UsersResponse, HttpError> {
        return try {
            val suffix = if (userId.isBlank()) "" else "?userId=$userId"
            val responseObject = client.get("$URL$endpoint$suffix")
            val response: UsersResponse = responseObject.body()
            when {
                responseObject.statusSuccess() -> Result.Success(data = response)
                else -> Result.Error(
                    error = HttpError.Communication(
                        code = responseObject.status.value,
                        codeDescription = responseObject.status.description
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(error = HttpError.Unknown(message = e.message.toString()))
        }
    }
}