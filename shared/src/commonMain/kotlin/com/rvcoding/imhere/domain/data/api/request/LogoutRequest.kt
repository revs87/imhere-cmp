package com.rvcoding.imhere.domain.data.api.request

import kotlinx.serialization.Serializable


@Serializable
data class LogoutRequest(
    val userId: String
)
