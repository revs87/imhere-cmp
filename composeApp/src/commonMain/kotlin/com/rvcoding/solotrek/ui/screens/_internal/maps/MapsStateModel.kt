package com.rvcoding.solotrek.ui.screens._internal.maps

import com.rvcoding.solotrek.CommonContext
import com.rvcoding.solotrek.data.location.LocationProviderFactory
import com.rvcoding.solotrek.data.permissions.PermissionHandlerFactory
import com.rvcoding.solotrek.data.permissions.PermissionStatus
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapsStateModel(
    private val locationProviderFactory: LocationProviderFactory,
    private val permissionHandlerFactory: PermissionHandlerFactory,
    private val coScope: CoroutineScope
) {
    private fun locationsFlow() = locationProviderFactory
        .create(CommonContext)
        .getLocation()

    val location: StateFlow<LocationDomain> = locationsFlow()
        .onEach { println("LocationUpdate: $it") }
        .stateIn(
            scope = coScope,
            started = WhileSubscribed(5000),
            initialValue = LocationDomain.Initial
        )

    private val permissionHandler = permissionHandlerFactory.create()

    fun requestLocationPermission() {
        coScope.launch {
            permissionHandler.requestLocationPermission()
        }
    }

    val locationPermissionStatus: StateFlow<PermissionStatus?> = flow {
        while (true) {
            emit(permissionHandler.checkLocationPermissionStatus())
            delay(1_000L)
        }
    }.stateIn(
        scope = coScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )
}