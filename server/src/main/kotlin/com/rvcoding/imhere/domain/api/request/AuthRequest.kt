package com.rvcoding.imhere.domain.api.request

import kotlinx.serialization.Serializable


@Serializable
data class AuthRequest(
    val userId: String,
    val password: String
)
