package com.rvcoding.imhere.data.location


//import com.rvcoding.imhere.domain.data.location.LocationDomain
//import kotlinx.coroutines.channels.awaitClose
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.callbackFlow
//import kotlinx.coroutines.suspendCancellableCoroutine
//import platform.CoreLocation.CLAuthorizationStatus
//import platform.CoreLocation.CLLocation
//import platform.CoreLocation.CLLocationManager
//import platform.CoreLocation.CLLocationManagerDelegateProtocol
//import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
//import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
//import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
//import platform.CoreLocation.kCLLocationAccuracyBest
//import platform.Foundation.NSError
//import platform.Foundation.timeIntervalSince1970
//import platform.darwin.NSObject
//import kotlin.coroutines.resume
//
//enum class PermissionStatus {
//    GRANTED,
//    DENIED,
//    // Use this when the user has selected "Don't ask again" on Android
//    // or has globally disabled location services.
//    PERMANENTLY_DENIED
//}
//
//interface PermissionHandler {
//    suspend fun requestLocationPermission(): PermissionStatus
//    suspend fun checkLocationPermissionStatus(): PermissionStatus
//}
//
//// This class does three things:
//// 1. Inherits from NSObject: This is required for any class that acts as a delegate for Apple's frameworks.
////    It's Kotlin's way of creating an object that the Objective-C runtime can understand.
//// 2. Implements KMPLocation: Our shared business interface.
//// 3. Implements CLLocationManagerDelegateProtocol: The delegate protocol for receiving location updates and events.
//class IosLocationProvider : NSObject(), KMPLocation, CLLocationManagerDelegateProtocol {
//
//    private val locationManager = CLLocationManager()
//
//    // A suspend function to handle the async permission request from the delegate.
//    private var permissionContinuation: ((PermissionStatus) -> Unit)? = null
//
//    // A callback to send location updates into the Kotlin Flow.
//    private var locationUpdateCallback: ((LocationDomain) -> Unit)? = null
//
//    override fun getLocation(interval: Long): Flow<LocationDomain> = callbackFlow {
//        // This callbackFlow builder is perfect for wrapping callback-based APIs into modern Flows.
//
//        // Set the callback that will send data into the flow's channel.
//        this@IosLocationProvider.locationUpdateCallback = { location ->
//            trySend(location) // Emits the location to the Flow collector.
//        }
//
//        // Configure the location manager.
//        locationManager.delegate = this@IosLocationProvider
//        locationManager.setDesiredAccuracy(kCLLocationAccuracyBest)
//        // CRITICAL FOR BACKGROUND: Tell iOS we need background updates.
//        // Requires the "Location updates" background capability in Xcode.
//        locationManager.setAllowsBackgroundLocationUpdates(true)
//
//        // If we already have permission, start immediately.
//        if (checkLocationPermissionStatus() == PermissionStatus.GRANTED) {
//            locationManager.startUpdatingLocation()
//        }
//
//        // This block is executed when the Flow is cancelled (e.g., ViewModel is cleared).
//        awaitClose {
//            locationManager.stopUpdatingLocation()
//            this@IosLocationProvider.locationUpdateCallback = null
//        }
//    }
//
//    // --- Implementation of the PermissionHandler from the previous answer ---
//
//    fun checkLocationPermissionStatus(): PermissionStatus {
//        return when (CLLocationManager.authorizationStatus()) {
//            kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> PermissionStatus.GRANTED
////            kCLAuthorizationStatusDenied, kCLAuthorizationStatusRestricted -> PermissionStatus.PERMANENTLY_DENIED
//            kCLAuthorizationStatusNotDetermined -> PermissionStatus.DENIED
//            else -> PermissionStatus.DENIED
//        }
//    }
//
//    suspend fun requestLocationPermission(): PermissionStatus {
//        val currentStatus = checkLocationPermissionStatus()
//        if (currentStatus == PermissionStatus.GRANTED) {
//            return currentStatus
//        }
//
//        // Wrap the callback-based permission request in a cancellable coroutine.
//        return suspendCancellableCoroutine { continuation ->
//            this.permissionContinuation = { status ->
//                continuation.resume(status)
//                this.permissionContinuation = null // Clean up
//            }
//            // This triggers the iOS permission dialog.
//            locationManager.requestAlwaysAuthorization()
//        }
//    }
//
//    // --- Delegate Methods (Called by iOS) ---
//
//    // This delegate method is called by the iOS system when the user responds to the permission dialog.
//    override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
//        // Resume the coroutine that is waiting for the permission result.
//        permissionContinuation?.invoke(checkLocationPermissionStatus())
//
//        // If permission was granted, start updating the location.
//        if (didChangeAuthorizationStatus == kCLAuthorizationStatusAuthorizedAlways || didChangeAuthorizationStatus == kCLAuthorizationStatusAuthorizedWhenInUse) {
//            manager.startUpdatingLocation()
//        }
//    }
//
//    // This delegate method is called by iOS whenever a new location is available.
//    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
//        didUpdateLocations.filterIsInstance<CLLocation>().firstOrNull()?.let { location ->
//            val locationDomain = LocationDomain(
//                latitude = 0.0, // TODO location.coordinate.useContents { latitude },
//                longitude = 0.0, // TODO location.coordinate.useContents { longitude },
//                altitude = location.altitude,
//                timestamp = (location.timestamp.timeIntervalSince1970 * 1000).toLong()
//            )
//            // Send the new location to our callback, which then emits it to the Flow.
//            locationUpdateCallback?.invoke(locationDomain)
//        }
//    }
//
//    // Called by iOS if location updates fail.
//    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
//        println("Location manager failed with error: $didFailWithError")
//    }
//}