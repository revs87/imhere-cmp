package com.rvcoding.imhere.domain.api.request

import com.rvcoding.imhere.model.UserState
import kotlinx.serialization.Serializable

@Serializable
data class UserStateRequest(
    val userId: String,
    val state: UserState
)