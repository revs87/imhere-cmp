package com.rvcoding.imhere.domain.api.response

import com.rvcoding.imhere.model.UserExposed
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class UserStateResponse(
    @Required val user: UserExposed?
)