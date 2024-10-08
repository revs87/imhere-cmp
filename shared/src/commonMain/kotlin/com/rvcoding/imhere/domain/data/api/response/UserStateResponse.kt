package com.rvcoding.imhere.domain.data.api.response

import com.rvcoding.imhere.domain.model.UserExposed
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class UserStateResponse(
    @Required val user: UserExposed?
)