package com.rvcoding.imhere.ui.screens.location

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin

@Preview
@Composable
fun LocationScreen(
    sm: LocationStateModel = getKoin().get<LocationStateModel>(),
    modifier: Modifier = Modifier
) {

}