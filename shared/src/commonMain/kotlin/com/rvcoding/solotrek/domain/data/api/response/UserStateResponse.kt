package com.rvcoding.solotrek.domain.data.api.response

import com.rvcoding.solotrek.domain.model.UserExposed
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class UserStateResponse(
    @Required val user: UserExposed?
)