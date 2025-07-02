package com.rvcoding.solotrek.domain.data.api.request

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val userId: String,
    val password: String
)
