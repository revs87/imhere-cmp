package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.data.location.PowerLevel.LEVEL_3
import com.rvcoding.imhere.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow

interface KMPLocation {
    fun getLocation(powerLevel: PowerLevel = LEVEL_3): Flow<LocationDomain>
}