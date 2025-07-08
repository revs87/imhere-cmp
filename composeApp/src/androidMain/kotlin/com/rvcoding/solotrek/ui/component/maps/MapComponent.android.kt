package com.rvcoding.solotrek.ui.component.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.rvcoding.solotrek.domain.data.location.LocationDomain

@Composable
actual fun MapComponent(coordinates: LocationDomain) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val coordinates = LatLng(coordinates.latitude, coordinates.longitude)
        val markerState = rememberUpdatedMarkerState(coordinates)
        val cameraPositionState = rememberUpdatedCameraPositionState(coordinates)

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
private fun rememberUpdatedCameraPositionState(newPosition: LatLng): CameraPositionState =
    remember { CameraPositionState(position = CameraPosition.fromLatLngZoom(newPosition, 15f)) }
        .apply { position = CameraPosition.fromLatLngZoom(newPosition, 15f) }