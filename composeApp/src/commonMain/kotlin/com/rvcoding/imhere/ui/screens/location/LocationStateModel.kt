package com.rvcoding.imhere.ui.screens.location

import com.rvcoding.imhere.data.location.LocationProviderFactory
import kotlinx.coroutines.CoroutineScope

class LocationStateModel(
    private val locationProviderFactory: LocationProviderFactory,
    private val scope: CoroutineScope
) {

}