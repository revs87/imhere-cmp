package com.rvcoding.imhere.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rvcoding.imhere.PlatformType
import com.rvcoding.imhere.api.response.UsersResponse
import com.rvcoding.imhere.client
import com.rvcoding.imhere.domain.model.User
import com.rvcoding.imhere.getPlatformType
import com.rvcoding.imhere.ui.component.UserCard
import com.rvcoding.imhere.ui.theme.AppTheme
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun UsersScreen(
    modifier: Modifier = Modifier
) {
    val users = remember { mutableStateListOf<User>() }

    LaunchedEffect(Unit) {
        try {
            val url = when (getPlatformType()) {
                is PlatformType.ANDROID -> "http://10.0.2.2:8080/rvc-imhere/users"
                is PlatformType.DESKTOP -> "http://0.0.0.0:8080/rvc-imhere/users"
                is PlatformType.IOS -> "http://0.0.0.0:8080/rvc-imhere/users"
                is PlatformType.WEB -> "http://0.0.0.0:8080/rvc-imhere/users"
            }
            val response: UsersResponse = client.get(url).body()
            users.addAll(response.users)
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
        }
    }

    AppTheme {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = users,
                    key = { it.id }
                ) { user ->
                    UserCard(
                        modifier = Modifier
                            .size(380.dp, 200.dp)
                            .padding(8.dp),
                        user = user,
                        onFollowClick = {},
                        onUnfollowClick = {},
                        onDetailsClick = {}
                    )
                }
            }
        }
    }

}