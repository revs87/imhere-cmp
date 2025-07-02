package com.rvcoding.solotrek.data.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.rvcoding.solotrek.CommonContext
import kotlinx.coroutines.suspendCancellableCoroutine

class AndroidPermissionHandler : PermissionHandler {
    private val activity: ComponentActivity = CommonContext.getActivity()
    private val context: Context by lazy { CommonContext.getContext() }

    override var permissionContinuation: ((PermissionStatus) -> Unit)? = null

    override suspend fun checkLocationPermissionStatus(): PermissionStatus {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return if (hasFineLocation || hasCoarseLocation) PermissionStatus.GRANTED else PermissionStatus.DENIED
    }

    override suspend fun requestLocationPermission(): PermissionStatus {
        val currentStatus = checkLocationPermissionStatus()
        if (currentStatus == PermissionStatus.GRANTED) {
            return PermissionStatus.GRANTED
        }

        // The shouldShowRequestPermissionRationale check helps determine if this is a permanent denial
        val showRationale = activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

        return suspendCancellableCoroutine { continuation ->
            this.permissionContinuation = { status ->
                if (status == PermissionStatus.DENIED && !showRationale) {
                    // If permission was denied and we shouldn't show a rationale, it's likely permanently denied.
                    continuation.resume(PermissionStatus.PERMANENTLY_DENIED) { cause, _, _ -> { } }
                } else {
                    continuation.resume(status) { cause, _, _ -> { } }
                }
            }
            PermissionLauncher.runAfterPermissionResult = { status ->
                permissionContinuation?.invoke(status)
                permissionContinuation = null
            }
            PermissionLauncher.launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}