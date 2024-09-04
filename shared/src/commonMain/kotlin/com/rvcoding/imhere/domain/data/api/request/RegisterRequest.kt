package com.rvcoding.imhere.domain.data.api.request

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    val userId: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
