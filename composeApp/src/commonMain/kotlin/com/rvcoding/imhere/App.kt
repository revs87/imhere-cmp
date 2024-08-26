package com.rvcoding.imhere

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.rvcoding.imhere.domain.util.toLocalDate
import com.rvcoding.imhere.ui.theme.AppTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Fetch Users")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Platform: $greeting")
                    Text("Registered users:")
                    FetchAndDisplayUsers("http://0.0.0.0:8080/rvc-imhere/users")
                }
            }
            /**
             * Desktop compile command:
             *     ./gradlew :composeApp:compileKotlinDesktop
             * */
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

@Serializable
data class UsersResponse(val users: List<User>)
@Serializable
data class User(
    @Required @SerialName("userId") val id: String,
    @Required val firstName: String = "",
    @Required val lastName: String = "",
    @Required val lastActivity: Long = 0L,
    @Required val state: String = "",
    @Required val coordinates: Coordinates = Coordinates(0.0, 0.0, 0L),
)
@Serializable
data class Coordinates(
    val lat: Double,
    val lon: Double,
    val timestamp: Long
)
@Composable
fun FetchAndDisplayUsers(url: String) {
    val users = remember { mutableStateListOf<User>() }
    val userContent = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        try {
            val response: UsersResponse = client.get(url).body()
            users.addAll(response.users)
            userContent.addAll(
                listOf("ID", "Name", "Last Activity", "State", "Coordinates", "Timestamp")
            )
            userContent.addAll(
                users.map { user ->
                    with(user) {
                        listOf(
                            id,
                            "$firstName $lastName",
                            lastActivity.toLocalDate(),
                            state,
                            "${coordinates.lat}, ${coordinates.lon}",
                            coordinates.timestamp.toLocalDate()
                        )
                    }
                }.flatten()
            )
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
        }
    }

    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        columns = StaggeredGridCells.Fixed(count = 6)
    ) {
        items(userContent) { content ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = content.ifBlank { "-" }
            )
        }
    }
}
