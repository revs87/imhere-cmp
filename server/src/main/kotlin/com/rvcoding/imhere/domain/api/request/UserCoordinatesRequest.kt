package com.rvcoding.imhere.domain.api.request

import com.rvcoding.imhere.domain.models.Coordinates
import com.rvcoding.imhere.domain.models.UserState
import kotlinx.serialization.Serializable

@Serializable
data class UserCoordinatesRequest(
    val userId: String,
    val coordinates: Coordinates
)