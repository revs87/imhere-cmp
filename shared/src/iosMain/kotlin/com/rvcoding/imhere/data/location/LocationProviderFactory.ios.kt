package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.CommonContext

actual class LocationProviderFactory actual constructor() {
    actual fun create(context: CommonContext): KMPLocation = FakeLocationProvider()//IosLocationProvider()
}