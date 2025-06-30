package com.rvcoding.imhere.domain.data.location

data class LocationDomain(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val timestamp: Long
) {
    companion object {
        val Initial = LocationDomain(
            latitude = 0.0,
            longitude = 0.0,
            altitude = 0.0,
            timestamp = 0L
        )
    }
}
