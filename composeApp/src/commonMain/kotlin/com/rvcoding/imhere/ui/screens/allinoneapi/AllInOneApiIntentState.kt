package com.rvcoding.imhere.ui.screens.allinoneapi

sealed class AllInOneApiIntent {
    data object Configuration : AllInOneApiIntent()
    data class Register(val userId: String, val password: String, val firstName: String, val lastName: String) : AllInOneApiIntent()
    data class Login(val userId: String, val password: String) : AllInOneApiIntent()
}

sealed interface AllInOneApiState {
    data object Initial : AllInOneApiState
    data object Loading : AllInOneApiState
    sealed interface Content : AllInOneApiState {
        data object Configuration : Content
        data object Register : Content
        data object Login : Content
    }
    data object Error : AllInOneApiState
}