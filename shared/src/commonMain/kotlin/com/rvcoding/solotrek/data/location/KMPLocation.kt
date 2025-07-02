package com.rvcoding.solotrek.data.location

import com.rvcoding.solotrek.data.location.PowerLevel.LEVEL_3
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import kotlinx.coroutines.flow.Flow

interface KMPLocation {
    fun getLocation(powerLevel: PowerLevel = LEVEL_3): Flow<LocationDomain>
}