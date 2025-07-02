package com.rvcoding.solotrek.data.permissions

actual class PermissionHandlerFactory {
    actual fun create(): PermissionHandler = AndroidPermissionHandler()
}