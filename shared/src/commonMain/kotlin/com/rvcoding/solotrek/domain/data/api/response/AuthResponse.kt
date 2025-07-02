package com.rvcoding.solotrek.domain.data.api.response

import com.rvcoding.solotrek.domain.data.api.AuthResult
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val code: Int,
    val type: String,
    val message: String,
    val result: AuthResult = AuthResult.KtorStatus.OK
)