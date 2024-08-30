package com.rvcoding.imhere.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserState(val state: Int) {
    IDLE(0),
    LOGGING(1),
    PAUSED(2);

    companion object {
        val Default = IDLE
        fun fromInt(value: Int) = entries.firstOrNull { it.state == value } ?: Default
    }
}
