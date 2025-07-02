package com.rvcoding.solotrek.data.location

import com.rvcoding.solotrek.CommonContext

actual class LocationProviderFactory actual constructor() {
    actual fun create(context: CommonContext): KMPLocation = FakeLocationProvider()
}