package com.rvcoding.solotrek

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.rvcoding.solotrek.data.permissions.PermissionLauncher
import com.rvcoding.solotrek.data.permissions.PermissionStatus

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(darkScrim = Color.TRANSPARENT, lightScrim = Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(darkScrim = Color.TRANSPARENT, lightScrim = Color.TRANSPARENT)
        )

        CommonContext.setActivity(this)

        PermissionLauncher.launcher = permissionRegister()

        setContent {
            MobileUI()
//            AdminUI()
        }
    }

    private fun permissionRegister() =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val isFineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val isCoarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            val status = if (isFineGranted || isCoarseGranted) {
                // For background, you might need to request ACCESS_BACKGROUND_LOCATION separately
                // after fine/coarse is granted. For simplicity, we handle foreground here.
                PermissionStatus.GRANTED
            } else {
                PermissionStatus.DENIED
            }
            PermissionLauncher.onPermissionResult(status)
        }
}
