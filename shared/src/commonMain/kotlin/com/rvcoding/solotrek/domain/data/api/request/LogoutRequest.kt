package com.rvcoding.solotrek.domain.data.api.request

import kotlinx.serialization.Serializable


@Serializable
data class LogoutRequest(
    val userId: String
)
