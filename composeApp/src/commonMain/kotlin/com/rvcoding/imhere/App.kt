package com.rvcoding.imhere

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rvcoding.imhere.di.IHKoin
import com.rvcoding.imhere.ui.screens.allinoneapi.AllInOneApiScreen
import com.rvcoding.imhere.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin

@Composable
@Preview
fun App() {
    startKoin {
        modules(IHKoin.modules)
    }

    AppTheme {
        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            containerColor = colorScheme.background,
            contentColor = contentColorFor(containerColor),
            //topBar = { }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
            ) {

                AllInOneApiScreen()

            }
        }
    }
}
