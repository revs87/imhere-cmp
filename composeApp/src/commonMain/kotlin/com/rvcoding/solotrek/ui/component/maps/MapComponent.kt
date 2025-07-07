package com.rvcoding.solotrek.ui.component.maps

import androidx.compose.runtime.Composable
import com.rvcoding.solotrek.domain.data.location.LocationDomain

@Composable
expect fun MapComponent(coordinates: LocationDomain)