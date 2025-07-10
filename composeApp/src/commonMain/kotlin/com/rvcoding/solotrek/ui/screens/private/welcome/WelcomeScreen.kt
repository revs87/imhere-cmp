package com.rvcoding.solotrek.ui.screens.private.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rvcoding.solotrek.Greeting
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun WelcomeScreenRoot() {
    WelcomeScreen()
}

@Composable
private fun WelcomeScreen() {
    val greeting = remember { Greeting().greet() }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Platform: $greeting")
    }
}


@Preview
@Composable
private fun WelcomeScreenPreview() {
    WelcomeScreen()
}