package com.rvcoding.imhere.ui.screens.allinoneapi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rvcoding.imhere.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin


@Preview
@Composable
fun AllInOneApiScreen(
    sm: AllInOneApiStateModel = getKoin().get<AllInOneApiStateModel>(),
    modifier: Modifier = Modifier
) {
    val content by sm.content.collectAsStateWithLifecycle()


    AppTheme {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column {
                Button(onClick = { sm.requestConfiguration() }) {
                    Text("Configuration")
                }
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = content
                )
            }

        }
    }

}