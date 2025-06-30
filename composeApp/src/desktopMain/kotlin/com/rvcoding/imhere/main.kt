package com.rvcoding.imhere

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.rvcoding.imhere.di.appModule
import com.rvcoding.imhere.di.initKoin

fun main() = application {
    initKoin(
        appModules = listOf(appModule)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        AdminUI()
    }
}