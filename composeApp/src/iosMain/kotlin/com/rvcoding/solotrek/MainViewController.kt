package com.rvcoding.solotrek

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import com.rvcoding.solotrek.di.appModule
import com.rvcoding.solotrek.di.initKoin

@ExperimentalComposeUiApi
fun MainViewController() = ComposeUIViewController(
    configure = {
        parallelRendering = true
        initKoin(
            appModules = listOf(appModule)
        )
    }) { MobileUI() }