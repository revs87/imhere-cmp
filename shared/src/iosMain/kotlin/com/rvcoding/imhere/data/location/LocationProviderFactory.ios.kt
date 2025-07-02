package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.CommonContext
import com.rvcoding.imhere.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow

actual class LocationProviderFactory actual constructor() {
    actual fun create(context: CommonContext): KMPLocation = object : KMPLocation {
        val locationProvider by lazy { IosLocationProvider() }

        override fun getLocation(powerLevel: PowerLevel): Flow<LocationDomain> = locationProvider.getLocation(powerLevel)
    }
}