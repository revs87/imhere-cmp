package com.rvcoding.solotrek.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rvcoding.solotrek.domain.navigation.Destination
import com.rvcoding.solotrek.ui.component.SecondaryButton
import com.rvcoding.solotrek.ui.navigation.core.NavigationAction
import com.rvcoding.solotrek.ui.navigation.core.ObserveAsEvents
import com.rvcoding.solotrek.ui.screens.poc.allinoneapi.AllInOneApiScreen
import com.rvcoding.solotrek.ui.screens.poc.location.LocationScreenRoot
import com.rvcoding.solotrek.ui.screens.poc.maps.MapsScreenRoot
import com.rvcoding.solotrek.ui.screens.private.welcome.WelcomeScreenRoot
import org.koin.compose.getKoin

@Composable
fun NavigationGraphRoot() {
    val vm: NavigationGraphStateModel = getKoin().get<NavigationGraphStateModel>()
    val navController: NavHostController = rememberNavController()
    val navigator = vm.navigator()

    /**
     * Navigation actions captured as flow events.
     * Generic handler promoting replay persistence from a Channel.
     * The dispatcher used is Dispatchers.Main.immediate for immediate UI execution.
     * */
    ObserveAsEvents(
        dispatchersProvider = vm.dispatchersProvider(),
        flow = navigator.navigationActions
    ) { action ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(
                action.destination
            ) {
                action.navOptions(this)
            }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }

    NavigationGraphScreen(
        navController = navController,
        startDestination = navigator.startDestination
    )
}

@Composable
fun NavigationGraphScreen(
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = NavigationGraphStateModel.INITIAL_DESTINATION
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Destination.Initial> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally
            ) {
                SecondaryButton(
                    text = "AllInOneApi",
                    onClick = { navController.navigate(Destination.AllInOneApi) }
                )
                SecondaryButton(
                    text = "Location",
                    onClick = { navController.navigate(Destination.Location) }
                )
                SecondaryButton(
                    text = "Maps",
                    onClick = { navController.navigate(Destination.Maps) }
                )
                SecondaryButton(
                    text = "Welcome",
                    onClick = { navController.navigate(Destination.Welcome) }
                )
            }

            /* Decides if user is logged in or not. */
        }

        /* poc */
        composable<Destination.AllInOneApi> {
            AllInOneApiScreen()
        }
        composable<Destination.Location> {
            LocationScreenRoot()
        }
        composable<Destination.Maps> {
            MapsScreenRoot()
        }

        /* public */
        composable<Destination.Welcome> {
            WelcomeScreenRoot()
        }
    }
}