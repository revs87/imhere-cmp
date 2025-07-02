package com.rvcoding.solotrek

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.rvcoding.solotrek.di.appModule
import com.rvcoding.solotrek.di.initKoin

fun main() = application {
    initKoin(
        appModules = listOf(appModule)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "SoloTrek Admin",
    ) {
        AdminUI()
    }
}