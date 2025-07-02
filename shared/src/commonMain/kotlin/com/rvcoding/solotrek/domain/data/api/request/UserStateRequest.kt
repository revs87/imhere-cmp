package com.rvcoding.solotrek.domain.data.api.request

import com.rvcoding.solotrek.domain.model.UserState
import kotlinx.serialization.Serializable

@Serializable
data class UserStateRequest(
    val userId: String,
    val state: UserState
)