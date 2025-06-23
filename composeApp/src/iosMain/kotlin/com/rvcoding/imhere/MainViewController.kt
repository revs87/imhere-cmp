package com.rvcoding.imhere

import androidx.compose.ui.window.ComposeUIViewController
import com.rvcoding.imhere.di.appModule
import com.rvcoding.imhere.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin(
            appModulesList = listOf(appModule)
        )
    }) { MainUI() }