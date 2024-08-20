package com.rvcoding.imhere.domain.models

import kotlinx.serialization.Serializable

@Serializable
enum class UserState(val state: Int) {
    IDLE(0),
    LOGGING(2),
    PAUSED(3);

    companion object {
        val Default = IDLE
        fun fromInt(value: Int) = entries.firstOrNull { it.state == value } ?: Default
    }
}
