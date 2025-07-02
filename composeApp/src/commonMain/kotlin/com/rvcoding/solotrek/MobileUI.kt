package com.rvcoding.solotrek

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rvcoding.solotrek.ui.screens._internal.location.LocationScreen
import com.rvcoding.solotrek.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MobileUI() {
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
                LocationScreen()
            }
        }
    }
}
