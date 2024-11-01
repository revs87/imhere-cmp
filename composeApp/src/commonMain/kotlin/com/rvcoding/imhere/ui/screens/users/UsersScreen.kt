package com.rvcoding.imhere.ui.screens.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rvcoding.imhere.ui.component.UserCard
import com.rvcoding.imhere.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin


@Preview
@Composable
fun UsersScreen(
    sm: UsersStateModel = getKoin().get<UsersStateModel>(),
    modifier: Modifier = Modifier,
) {
    val users by sm.users.collectAsState()

    AppTheme {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = users,
                    key = { index, _ -> index }
                ) { _, user ->
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