package com.rvcoding.solotrek.domain.data.api.request

import kotlinx.serialization.Serializable


@Serializable
data class UnsubscribeRequest(
    val userId: String,
    val userIdToUnsubscribe: String
)
