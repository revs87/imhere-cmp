package com.rvcoding.imhere.domain.models

enum class UserState(val state: Int) {
    OFFLINE(0),
    ONLINE(1),
    LOGGING(2),
    PAUSED(3);

    companion object {
        val Default = OFFLINE
        fun fromInt(value: Int) = entries.firstOrNull { it.state == value } ?: Default
    }
}
