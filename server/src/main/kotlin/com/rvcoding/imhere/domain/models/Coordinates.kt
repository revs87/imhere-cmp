package com.rvcoding.imhere.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val lat: Double,
    val lon: Double,
    val timestamp: Long
)