package com.rvcoding.imhere.domain.data.api.error


sealed interface AuthError: Error {
    data object INVALID_PARAMETERS
    data object CRITERIA_NOT_MET
    data object UNAUTHORIZED
    enum class Register : AuthError {
        USER_ALREADY_REGISTERED
    }
}
