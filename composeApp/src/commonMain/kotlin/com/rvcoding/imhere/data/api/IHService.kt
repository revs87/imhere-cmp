package com.rvcoding.imhere.data.api

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.AuthResult.LoginResult
import com.rvcoding.imhere.domain.data.api.AuthResult.RegisterResult
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.api.error.HttpError
import com.rvcoding.imhere.domain.data.api.request.LoginRequest
import com.rvcoding.imhere.domain.data.api.request.RegisterRequest
import com.rvcoding.imhere.domain.data.api.response.AuthResponse
import com.rvcoding.imhere.domain.data.api.response.ConfigurationResponse
import com.rvcoding.imhere.domain.data.api.response.UsersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class IHService(
    private val client: HttpClient
) : IHApi {
    override suspend fun getConfiguration(): Result<ConfigurationResponse, HttpError> {
        return try {
            val responseObject = client.get("${IHApi.URL}${Route.Configuration.path}")
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
            Result.Error(error = HttpError.Unknown(message = e.message.toString()))
        }
    }
    override suspend fun register(userId: String, password: String, firstName: String, lastName: String): Result<AuthResponse, HttpError> {
        return try {
            val responseObject = client.post("${IHApi.URL}${Route.Register.path}") {
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
            val responseObject = client.post("${IHApi.URL}${Route.Login.path}") {
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
    override suspend fun users(): Result<UsersResponse, HttpError> {
        return try {
            val responseObject = client.get("${IHApi.URL}${Route.Users.path}")
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