package com.rvcoding.imhere.domain.data.api.response

import com.rvcoding.imhere.domain.data.api.AuthResult
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val code: Int,
    val type: String,
    val message: String,
    val result: AuthResult = AuthResult.KtorStatus.OK
)