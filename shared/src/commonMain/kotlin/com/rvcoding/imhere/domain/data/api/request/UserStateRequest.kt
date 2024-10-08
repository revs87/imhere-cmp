package com.rvcoding.imhere.domain.data.api.request

import com.rvcoding.imhere.domain.model.UserState
import kotlinx.serialization.Serializable

@Serializable
data class UserStateRequest(
    val userId: String,
    val state: UserState
)