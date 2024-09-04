package com.rvcoding.imhere.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val lat: Double,
    val lon: Double,
    val timestamp: Long
)