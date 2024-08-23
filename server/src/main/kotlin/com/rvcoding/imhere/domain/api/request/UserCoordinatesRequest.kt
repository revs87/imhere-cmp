package com.rvcoding.imhere.domain.api.request

import kotlinx.serialization.Serializable

@Serializable
data class UserCoordinatesRequest(
    val userId: String,
    val lat: Double,
    val lon: Double,
    val timestamp: Long? = null,
)