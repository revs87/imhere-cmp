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
import com.rvcoding.imhere.ui.screens.UsersScreen
import com.rvcoding.imhere.ui.theme.AppTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
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
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { showContent = !showContent }) {
                        Text("Fetch Users")
                    }
                    AnimatedVisibility(showContent) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Registered users:")
                            UsersScreen()
                        }
                    }
                }


            }
        }
    }
}

val client = HttpClient(CIO) {
    install(ContentNegotiation) { json() }
//    install(Logging) {
//        logger = Logger.DEFAULT
//        level = LogLevel.ALL
//    }
    install(HttpTimeout) {
        requestTimeoutMillis = 30000
        connectTimeoutMillis = 30000
        socketTimeoutMillis = 30000
    }
}.also {
    it.plugin(HttpSend).intercept { request ->
        println("Intercepting request: ${request.url}")
        request.headers.append("X-Custom-Header", "MyValue")
        val call = execute(request)
        println("Response received: ${call.response.status}")
        call
    }
}
