package com.rvcoding.solotrek.data.location

import com.rvcoding.solotrek.CommonContext
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow

actual class LocationProviderFactory actual constructor() {
    actual fun create(context: CommonContext): KMPLocation = object : KMPLocation {
        val locationProvider by lazy { IosLocationProvider() }

        override fun getLocation(powerLevel: PowerLevel): Flow<LocationDomain> = locationProvider.getLocation(powerLevel)
    }
}