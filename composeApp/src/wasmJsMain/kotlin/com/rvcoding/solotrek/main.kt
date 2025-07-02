package com.rvcoding.solotrek

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.rvcoding.solotrek.di.appModule
import com.rvcoding.solotrek.di.initKoin
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(
        appModules = listOf(appModule)
    )
    ComposeViewport(document.body!!) {
        AdminUI()
    }
}