package com.rvcoding.solotrek.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rvcoding.solotrek.domain.model.User
import com.rvcoding.solotrek.util.toLocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    user: User = User.Default,
    onFollowClick: () -> Unit = {},
    onUnfollowClick: () -> Unit = {},
    onDetailsClick: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(),
        border = null,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .clickable { onDetailsClick.invoke() }
                .padding(8.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Column {
                Text(text = user.id)
                Text(text = "Name: ${"${user.firstName} ${user.firstName}".ifBlank{ "-" }}")
                Text(text = "State: ${user.state}")
                Text(text = "Last coordinates: ${user.coordinates.lat}, ${user.coordinates.lon} ${user.coordinates.timestamp.toLocalDate()}")
            }
        }
    }
}