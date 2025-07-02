package com.rvcoding.solotrek.domain.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResult {
    @Serializable
    @SerialName("Success")
    data object Success : ApiResult()
    @Serializable
    @SerialName("Failure")
    data class Failure(val message: String) : ApiResult()
}

@Serializable
data class ApiResponse(
    val result: ApiResult
)