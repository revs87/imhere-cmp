package com.rvcoding.solotrek.data.permissions

import androidx.activity.result.ActivityResultLauncher

actual object PermissionLauncher {
    lateinit var launcher: ActivityResultLauncher<Array<String>>

    var runAfterPermissionResult: ((PermissionStatus) -> Unit)? = null

    fun onPermissionResult(result: PermissionStatus) {
        runAfterPermissionResult?.invoke(result)
    }
}