package com.rvcoding.solotrek.domain.navigation

sealed interface Action {
    sealed interface Registration : Action {
        data class EditEmail(val email: String) : Registration
        data class EditPassword(val passwd: String) : Registration
        data class Register(val data: String) : Registration
    }

    sealed interface Welcome : Action {
        data object SwipeRight : Welcome
        data object SwipeLeft : Welcome
        data object Proceed : Welcome
    }
}