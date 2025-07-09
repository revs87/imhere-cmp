package com.rvcoding.solotrek.ui.component.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
actual fun MapComponent(coordinates: LocationDomain) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val coordinates = LatLng(coordinates.latitude, coordinates.longitude)
        val markerState = rememberUpdatedMarkerState(coordinates)
        val coScope = rememberCoroutineScope()
        val cameraPositionState = rememberUpdatedCameraPositionState(coordinates, coScope)

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.TERRAIN
            )
        ) {
            Marker(
                state = markerState,
                title = "Your Location",
                snippet = "snippet"
            )
        }
    }
}

@Composable
private fun rememberUpdatedMarkerState(newPosition: LatLng): MarkerState =
    remember { MarkerState(position = newPosition) }
        .apply { position = newPosition }


@Composable
private fun rememberUpdatedCameraPositionState(
    newPosition: LatLng,
    coScope: CoroutineScope
): CameraPositionState {
    val newCameraPosition = CameraPosition.fromLatLngZoom(newPosition, 16f)
    return remember { CameraPositionState(position = newCameraPosition) }
        .apply {
            coScope.launch {
                animate(
                    update = CameraUpdateFactory.newCameraPosition(newCameraPosition),
                    durationMs = 2_000
                )
            }
            position = newCameraPosition
        }
}
