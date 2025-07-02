package com.rvcoding.solotrek.domain.data.api.response

import com.rvcoding.solotrek.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<User>
) {
    companion object {
        fun empty() = UsersResponse(emptyList())
    }
}