package com.rvcoding.solotrek.ui.navigation.core

import androidx.navigation.NavOptionsBuilder
import com.rvcoding.solotrek.domain.navigation.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
}

class DefaultNavigator(
    override val startDestination: Destination
): Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    /**
     * Prevents navigating to the same destination multiple times.
     * */
    private var lastDestination: Destination? = null

    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        if (destination != lastDestination) {
            _navigationActions.send(NavigationAction.Navigate(
                destination = destination,
                navOptions = navOptions
            ))
            lastDestination = destination
        }
    }

    override suspend fun navigateUp() {
        lastDestination = null
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}