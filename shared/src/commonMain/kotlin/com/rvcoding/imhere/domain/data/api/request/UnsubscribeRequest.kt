package com.rvcoding.imhere.domain.data.api.request

import kotlinx.serialization.Serializable


@Serializable
data class UnsubscribeRequest(
    val userId: String,
    val userIdToUnsubscribe: String
)
