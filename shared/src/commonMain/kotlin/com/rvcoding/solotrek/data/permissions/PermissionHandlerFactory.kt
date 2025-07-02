package com.rvcoding.solotrek.data.permissions

interface PermissionHandler {
    var permissionContinuation: ((PermissionStatus) -> Unit)?
    suspend fun checkLocationPermissionStatus(): PermissionStatus
    suspend fun requestLocationPermission(): PermissionStatus
}

expect class PermissionHandlerFactory() {
    fun create(): PermissionHandler
}