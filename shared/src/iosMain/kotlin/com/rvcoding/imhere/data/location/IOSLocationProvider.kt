package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class IOSLocationProvider : KMPLocation {
    override fun getLocation(interval: Long): Flow<LocationDomain> = flowOf(LocationDomain(1.0, 2.0, 3.0, 4))
}