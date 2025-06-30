package com.rvcoding.imhere.ui.screens._internal.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rvcoding.imhere.data.permissions.PermissionStatus
import com.rvcoding.imhere.ui.theme.AppTheme
import com.rvcoding.imhere.util.toLocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin


@Composable
fun LocationScreen(
    sm: LocationStateModel = getKoin().get<LocationStateModel>()
) {
    val location = sm.location.collectAsStateWithLifecycle()
    val permissionStatus = sm.locationPermissionStatus.collectAsStateWithLifecycle()

    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Permission status: ${permissionStatus.value}")
                if (permissionStatus.value != PermissionStatus.GRANTED) {
                    Button(
                        onClick = { sm.requestLocationPermission() }
                    ) {
                        Text(text = "Request Location permissions")
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    userScrollEnabled = true
                ) {
                    items(
                        count = location.value.size,
                        key = { location.value[it].toString() }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = location.value[it].timestamp.toLocalDate(),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "lat:${location.value[it].latitude}  lon:${location.value[it].longitude}  alt:${location.value[it].altitude}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LocationScreenPreview() {
    LocationScreen()
}