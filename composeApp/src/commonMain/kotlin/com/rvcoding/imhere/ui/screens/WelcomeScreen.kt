package com.rvcoding.imhere.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rvcoding.imhere.Greeting
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun WelcomeScreen() {
    val greeting = remember { Greeting().greet() }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Platform: $greeting")
    }
}