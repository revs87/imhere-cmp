package com.rvcoding.imhere.domain.data.api.error

import com.rvcoding.imhere.domain.data.api.response.AuthResponse


sealed interface HttpError : Error {
    data class Auth(val response: AuthResponse) : HttpError
    data class Communication(val code: Int, val codeDescription: String) : HttpError
    data class Unknown(val message: String = "Unknown error") : HttpError
}
