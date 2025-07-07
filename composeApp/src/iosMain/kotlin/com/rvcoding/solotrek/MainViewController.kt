package com.rvcoding.solotrek

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import com.rvcoding.solotrek.di.appModule
import com.rvcoding.solotrek.di.initKoin
import platform.UIKit.UIViewController

@ExperimentalComposeUiApi
fun MainViewController(
    mapUIViewController: () -> UIViewController
) = ComposeUIViewController(
    configure = {
        parallelRendering = true
        initKoin(
            appModules = listOf(appModule)
        )
    }
) {
    mapViewController = mapUIViewController
    MobileUI()
}

lateinit var mapViewController: () -> UIViewController