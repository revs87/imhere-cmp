package com.rvcoding.imhere.data.permissions

enum class PermissionStatus {
    GRANTED,
    DENIED,
    // Use this when the user has selected "Don't ask again" on Android
    // or has globally disabled location services.
    PERMANENTLY_DENIED
}
