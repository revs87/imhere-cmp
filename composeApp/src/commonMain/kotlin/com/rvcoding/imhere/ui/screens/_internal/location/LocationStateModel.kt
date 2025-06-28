package com.rvcoding.imhere.ui.screens._internal.location

import com.rvcoding.imhere.data.location.LocationProviderFactory
import kotlinx.coroutines.CoroutineScope

class LocationStateModel(
    private val locationProviderFactory: LocationProviderFactory,
    private val scope: CoroutineScope
) {

}