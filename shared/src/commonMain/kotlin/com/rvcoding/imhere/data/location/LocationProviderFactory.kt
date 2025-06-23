package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.CommonContext

expect class LocationProviderFactory() {
    fun create(context: CommonContext): KMPLocation
}