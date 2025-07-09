package com.rvcoding.solotrek

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import com.rvcoding.solotrek.di.appModule
import com.rvcoding.solotrek.di.initKoin
import platform.UIKit.UIViewController

@ExperimentalComposeUiApi
fun MainViewController(
    mapUIControllerFactory: () -> UIViewController,
    mapUIControllerUpdate: (lat: Double, long: Double) -> Unit
) = ComposeUIViewController(
    configure = {
        parallelRendering = true
        initKoin(
            appModules = listOf(appModule)
        )
    }
) {
    mapViewControllerFactory = mapUIControllerFactory
    mapViewControllerUpdate = mapUIControllerUpdate
    MobileUI()
}

lateinit var mapViewControllerFactory: () -> UIViewController
lateinit var mapViewControllerUpdate: (lat: Double, long: Double) -> Unit

