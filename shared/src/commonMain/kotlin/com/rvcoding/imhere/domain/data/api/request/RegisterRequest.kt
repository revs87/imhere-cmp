package com.rvcoding.imhere.domain.data.api.request

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    @Required val userId: String,
    @Required val password: String,
    @Required val firstName: String,
    @Required val lastName: String
)
