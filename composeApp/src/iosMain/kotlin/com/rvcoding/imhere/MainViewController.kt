package com.rvcoding.imhere

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import com.rvcoding.imhere.di.appModule
import com.rvcoding.imhere.di.initKoin

@ExperimentalComposeUiApi
fun MainViewController() = ComposeUIViewController(
    configure = {
        parallelRendering = true
        initKoin(
            appModules = listOf(appModule)
        )
    }) { MainUI() }