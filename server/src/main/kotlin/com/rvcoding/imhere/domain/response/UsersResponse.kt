package com.rvcoding.imhere.domain.response

import com.rvcoding.imhere.domain.models.User
import com.rvcoding.imhere.domain.models.UserExposed
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<UserExposed>
)