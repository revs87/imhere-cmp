package com.rvcoding.imhere.domain.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SubscriptionResult {
    @Serializable
    @SerialName("Success")
    data object Success : SubscriptionResult()
    @Serializable
    @SerialName("Failure")
    data class Failure(val message: String) : SubscriptionResult()
}

@Serializable
data class SubscriptionResponse(
    val result: SubscriptionResult
)