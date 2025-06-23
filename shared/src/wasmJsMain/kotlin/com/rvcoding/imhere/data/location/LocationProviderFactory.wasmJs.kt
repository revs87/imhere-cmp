package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.CommonContext
import com.rvcoding.imhere.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class LocationProviderFactory actual constructor() {
    actual fun create(context: CommonContext): KMPLocation = object : KMPLocation {
        override fun getLocation(interval: Long): Flow<LocationDomain> =
            flowOf(LocationDomain(0.0, 0.0, 0.0, 0L))
    }
}