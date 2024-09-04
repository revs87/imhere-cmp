package com.rvcoding.imhere.domain.api.response

import com.rvcoding.imhere.domain.model.UserExposed
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<UserExposed>
)