package com.rvcoding.imhere.data.location

import com.rvcoding.imhere.data.permissions.PermissionHandlerFactory
import com.rvcoding.imhere.data.permissions.PermissionStatus
import com.rvcoding.imhere.data.permissions.isGranted
import com.rvcoding.imhere.domain.data.location.LocationDomain
import com.rvcoding.imhere.util.platformCoroutineDispatcherIO
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.Foundation.timeIntervalSince1970
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
// This class does three things:
// 1. Inherits from NSObject: This is required for any class that acts as a delegate for Apple's frameworks.
//    It's Kotlin's way of creating an object that the Objective-C runtime can understand.
// 2. Implements KMPLocation: Our shared business interface.
// 3. Implements CLLocationManagerDelegateProtocol: The delegate protocol for receiving location updates and events.
class IosLocationProvider : NSObject(), KMPLocation, CLLocationManagerDelegateProtocol {

    private val coScope: CoroutineScope = CoroutineScope(CoroutineName("IosLocationProvider") + platformCoroutineDispatcherIO())

    private val locationManager = CLLocationManager()
    private val permissionHandler = PermissionHandlerFactory().create()

    // A callback to send location updates into the Kotlin Flow.
    private var locationUpdateCallback: ((LocationDomain) -> Unit)? = null


    override fun getLocation(interval: Long): Flow<LocationDomain> = callbackFlow {
        // Set the callback that will send data into the flow's channel.
        this@IosLocationProvider.locationUpdateCallback = { location ->
            trySend(location) // Emits the location to the Flow collector.
        }

        locationManager.delegate = this@IosLocationProvider
        locationManager.setDesiredAccuracy(kCLLocationAccuracyBest)
        // CRITICAL FOR BACKGROUND: Tell iOS we need background updates.
        // Requires the "Location updates" background capability in Xcode.
        locationManager.setAllowsBackgroundLocationUpdates(true)
        locationManager.setShowsBackgroundLocationIndicator(true)

        // If we already have permission, start immediately.
        if (permissionHandler.checkLocationPermissionStatus() == PermissionStatus.GRANTED) {
            locationManager.startUpdatingLocation()
//            locationManager.requestLocation()
        }

        // This block is executed when the Flow is cancelled (e.g., ViewModel is cleared).
        awaitClose {
            locationManager.stopUpdatingLocation()
            this@IosLocationProvider.locationUpdateCallback = null
        }
    }

    // --- Delegate Methods (Called by iOS) ---

    // This delegate method is called by the iOS system when the user responds to the permission dialog.
    override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
        coScope.launch {
            // Resume the coroutine that is waiting for the permission result.
            permissionHandler.permissionContinuation?.invoke(permissionHandler.checkLocationPermissionStatus())

            // If permission was granted, start updating the location.
            if (didChangeAuthorizationStatus.isGranted()) {
                manager.accuracyAuthorization
                manager.startUpdatingLocation()
            }
        }
    }

    // This delegate method is called by iOS whenever a new location is available.
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        didUpdateLocations.filterIsInstance<CLLocation>().firstOrNull()?.let { location ->
            val locationDomain = LocationDomain(
                latitude = location.coordinate.useContents { latitude },
                longitude = location.coordinate.useContents { longitude },
                altitude = location.altitude,
                timestamp = (location.timestamp.timeIntervalSince1970 * 1000).toLong()
            )
            // Send the new location to our callback, which then emits it to the Flow.
            locationUpdateCallback?.invoke(locationDomain)
        }
    }

    // Called by iOS if location updates fail.
    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        println("Location manager failed with error: $didFailWithError")
    }
}