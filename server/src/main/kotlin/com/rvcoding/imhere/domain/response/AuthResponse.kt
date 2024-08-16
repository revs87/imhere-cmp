package com.rvcoding.imhere.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val code: Int,
    val type: String,
    val message: String
)