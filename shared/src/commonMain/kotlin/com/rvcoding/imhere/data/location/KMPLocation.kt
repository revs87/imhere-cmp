package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow

interface KMPLocation {
    fun getLocation(interval: Long = 3000L): Flow<LocationDomain>
}