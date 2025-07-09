package com.rvcoding.solotrek.ui.screens._internal.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rvcoding.solotrek.AppSecrets
import com.rvcoding.solotrek.data.permissions.PermissionStatus
import com.rvcoding.solotrek.domain.data.location.LocationDomain
import com.rvcoding.solotrek.ui.component.maps.MapComponent
import com.rvcoding.solotrek.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin


@Composable
fun MapsScreenRoot() {
    val sm = getKoin().get<MapsStateModel>()

    MapsScreen(
        location = sm.location.collectAsStateWithLifecycle(),
        permissionStatus = sm.locationPermissionStatus.collectAsStateWithLifecycle(),
        requestLocationPermission = sm::requestLocationPermission
    )
}

@Composable
fun MapsScreen(
    location: State<LocationDomain>,
    permissionStatus: State<PermissionStatus?>,
    requestLocationPermission: () -> Unit
) {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Permission status: ${permissionStatus.value}", fontSize = 12.sp)
                if (permissionStatus.value != PermissionStatus.GRANTED) {
                    Button(
                        onClick = { requestLocationPermission() }
                    ) {
                        Text(text = "Request Location permissions")
                    }
                }
                Text(text = AppSecrets.mapsApiKey, fontSize = 12.sp)
                Text(text = "Coordinates: ${location.value.latitude}, ${location.value.longitude}", fontSize = 12.sp)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MapComponent(coordinates = location.value)
                }
            }
        }
    }
}

@Preview
@Composable
private fun MapsScreenPreview() {
    MapsScreen(
        location = mutableStateOf(LocationDomain.Initial),
        permissionStatus = mutableStateOf(PermissionStatus.GRANTED),
        requestLocationPermission = {}
    )
}