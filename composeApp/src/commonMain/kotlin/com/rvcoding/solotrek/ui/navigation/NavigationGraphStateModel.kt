package com.rvcoding.solotrek.ui.navigation

import com.rvcoding.solotrek.domain.navigation.Destination
import com.rvcoding.solotrek.ui.navigation.core.Navigator
import com.rvcoding.solotrek.util.DispatchersProvider

class NavigationGraphStateModel(
    private val navigator: Navigator,
    private val dispatchersProvider: DispatchersProvider
) {
    fun navigator() = navigator
    fun dispatchersProvider() = dispatchersProvider

    companion object Companion {
        val INITIAL_DESTINATION = Destination.Initial
    }
}