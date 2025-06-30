package com.rvcoding.imhere

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.rvcoding.imhere.di.appModule
import com.rvcoding.imhere.di.initKoin
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