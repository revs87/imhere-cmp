package com.rvcoding.solotrek.data.permissions

actual class PermissionHandlerFactory {
    actual fun create(): PermissionHandler = object : PermissionHandler {
        override var permissionContinuation: ((PermissionStatus) -> Unit)? = null
        override suspend fun requestLocationPermission(): PermissionStatus = PermissionStatus.PERMANENTLY_DENIED
        override suspend fun checkLocationPermissionStatus(): PermissionStatus = PermissionStatus.PERMANENTLY_DENIED
    }
}