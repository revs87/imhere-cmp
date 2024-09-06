package com.rvcoding.imhere.ui.screens.allinoneapi

sealed class AllInOneApiIntent {
    data object Configuration : AllInOneApiIntent()
    data class Register(val userId: String, val password: String, val firstName: String, val lastName: String) : AllInOneApiIntent()
    data class Login(val userId: String, val password: String) : AllInOneApiIntent()
}

sealed class AllInOneApiState {
    data object Initial : AllInOneApiState()
    data object Loading : AllInOneApiState()
    data object Content : AllInOneApiState()
    data object Error : AllInOneApiState()
}