package com.rvcoding.solotrek.domain

import kotlinx.serialization.Serializable
import com.rvcoding.solotrek.domain.data.api.error.Error as LayerError


@Serializable
sealed interface Result<out D, out E : LayerError> {
    @Serializable
    data class Success<out D, out E : LayerError>(val data: D) : Result<D, E>
    @Serializable
    data class Error<out D, out E : LayerError>(val error: E) : Result<D, E>
}
