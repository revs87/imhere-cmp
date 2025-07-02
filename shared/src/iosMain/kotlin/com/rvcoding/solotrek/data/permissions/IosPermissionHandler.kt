package com.rvcoding.solotrek.data.permissions

import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted

class IosPermissionHandler(
    private val locationManager: CLLocationManager
) : PermissionHandler {
    // A suspend function to handle the async permission request from the delegate.
    override var permissionContinuation: ((PermissionStatus) -> Unit)? = null

    override suspend fun requestLocationPermission(): PermissionStatus {
        val currentStatus = checkLocationPermissionStatus()
        if (currentStatus == PermissionStatus.GRANTED) {
            return currentStatus
        }

        // Wrap the callback-based permission request in a cancellable coroutine.
        return suspendCancellableCoroutine { continuation ->
            permissionContinuation = { status ->
                continuation.resume(status) { cause, _, _ -> { } }
                permissionContinuation = null // Clean up
            }
            locationManager.requestAlwaysAuthorization()
        }
    }

    override suspend fun checkLocationPermissionStatus(): PermissionStatus = when (CLLocationManager.authorizationStatus()) {
        kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> PermissionStatus.GRANTED
        kCLAuthorizationStatusDenied, kCLAuthorizationStatusRestricted -> PermissionStatus.PERMANENTLY_DENIED
        kCLAuthorizationStatusNotDetermined -> PermissionStatus.DENIED
        else -> PermissionStatus.DENIED
    }
}