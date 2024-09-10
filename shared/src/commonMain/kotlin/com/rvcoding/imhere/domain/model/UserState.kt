package com.rvcoding.imhere.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserState(val state: Int) {
    IDLE(0),
    LOGGING(1),
    PAUSED(2),
    LOGGED_OUT(3);

    companion object {
        val Default = LOGGED_OUT
        fun fromInt(value: Int) = entries.firstOrNull { it.state == value } ?: Default
    }
}
