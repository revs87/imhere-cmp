package com.rvcoding.solotrek.ui.navigation.core

import androidx.navigation.NavOptionsBuilder
import com.rvcoding.solotrek.domain.navigation.Destination


sealed interface NavigationAction {

    data class Navigate(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}