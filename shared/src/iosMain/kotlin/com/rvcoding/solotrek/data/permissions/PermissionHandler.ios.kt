package com.rvcoding.solotrek.data.permissions

import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse

actual class PermissionHandlerFactory {
    private val locationManager = CLLocationManager()

    actual fun create(): PermissionHandler = IosPermissionHandler(locationManager)
}

fun CLAuthorizationStatus.isGranted(): Boolean = when(this) {
    kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> true
    else -> false
}
