package com.rvcoding.imhere

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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


                var showContent by remember { mutableStateOf(false) }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { showContent = !showContent }) {
                        Text("Fetch Users")
                    }
                    AnimatedVisibility(showContent) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Registered users:")
                            //UsersScreen()
                            AllInOneApiScreen()
                        }
                    }
                }


            }
        }
    }
}
