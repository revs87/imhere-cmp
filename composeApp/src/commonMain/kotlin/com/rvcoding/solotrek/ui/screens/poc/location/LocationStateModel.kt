package com.rvcoding.solotrek.ui.screens.poc.location

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LocationStateModel(
    private val locationProviderFactory: LocationProviderFactory,
    private val permissionHandlerFactory: PermissionHandlerFactory,
    private val coScope: CoroutineScope
) {
    private fun locationsFlow() = locationProviderFactory
        .create(CommonContext)
        .getLocation()
        .map { location -> listOf(location) }
        .runningFold(
            initial = listOf(LocationDomain.Initial)
        ) { old, new ->
            old + new
        }
        .map { list -> list.filter { it.timestamp > 0 } }

    val location: StateFlow<List<LocationDomain>> = locationsFlow()
        .onEach { println("LocationUpdate: $it") }
        .stateIn(
            scope = coScope,
            started = WhileSubscribed(5000),
            initialValue = listOf(LocationDomain.Initial)
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