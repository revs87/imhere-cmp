package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.CommonContext
import com.rvcoding.imhere.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

expect class LocationProviderFactory() {
    fun create(context: CommonContext): KMPLocation
}

class FakeLocationProvider : KMPLocation {
    override fun getLocation(powerLevel: PowerLevel): Flow<LocationDomain> = flowOf(
        LocationDomain(1.0, 2.0, 3.0, 4)
    )
}