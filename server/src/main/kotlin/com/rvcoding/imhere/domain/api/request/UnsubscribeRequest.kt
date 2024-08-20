package com.rvcoding.imhere.domain.api.request

import kotlinx.serialization.Serializable


@Serializable
data class UnsubscribeRequest(
    val userId: String,
    val userIdToUnsubscribe: String
)
