package com.rvcoding.solotrek.ui.component.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import com.rvcoding.solotrek.mapViewController

@Composable
actual fun MapComponent(coordinates: LocationDomain) {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}