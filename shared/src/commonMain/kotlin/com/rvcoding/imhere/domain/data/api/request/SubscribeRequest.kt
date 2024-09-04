package com.rvcoding.imhere.domain.data.api.request

import kotlinx.serialization.Serializable


@Serializable
data class SubscribeRequest(
    val userId: String,
    val userIdToSubscribe: String
)
