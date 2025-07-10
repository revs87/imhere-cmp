package com.rvcoding.solotrek.domain.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object AllInOneApi : Destination

    @Serializable
    data object Location : Destination

    @Serializable
    data object Maps : Destination

    @Serializable
    data object Initial : Destination

    @Serializable
    data object Registration : Destination

    @Serializable
    data object Login : Destination

    @Serializable
    data object Welcome : Destination

    @Serializable
    data object Home : Destination
}