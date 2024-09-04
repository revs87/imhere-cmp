package com.rvcoding.imhere.domain


sealed interface DataError : Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
    data class Message(val message: String) : DataError
}
