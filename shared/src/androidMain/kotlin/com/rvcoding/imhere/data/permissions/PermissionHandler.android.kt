package com.rvcoding.imhere.data.permissions

actual class PermissionHandlerFactory {
    actual fun create(): PermissionHandler = AndroidPermissionHandler()
}