package com.rvcoding.imhere.domain


sealed interface HttpError : Error {
    data class Connection(val code: Int, val codeDescription: String) : HttpError
    data object Unknown : HttpError
}
