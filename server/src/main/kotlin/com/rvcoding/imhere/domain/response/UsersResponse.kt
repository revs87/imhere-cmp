package com.rvcoding.imhere.domain.response

import com.rvcoding.imhere.domain.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<User>
)