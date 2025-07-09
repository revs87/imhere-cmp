package com.rvcoding.solotrek.ui.component.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import com.rvcoding.solotrek.mapViewControllerFactory
import com.rvcoding.solotrek.mapViewControllerUpdate


@Composable
actual fun MapComponent(coordinates: LocationDomain) {
    UIKitViewController(
        modifier = Modifier.fillMaxSize(),
        factory = mapViewControllerFactory,
        update = { uiViewController ->
            mapViewControllerUpdate.invoke(coordinates.latitude, coordinates.longitude)
        },
    )

}
