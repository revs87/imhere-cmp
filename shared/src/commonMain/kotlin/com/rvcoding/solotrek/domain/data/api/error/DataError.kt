package com.rvcoding.solotrek.domain.data.api.error


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
