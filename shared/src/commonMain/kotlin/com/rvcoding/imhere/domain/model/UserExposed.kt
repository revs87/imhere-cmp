package com.rvcoding.imhere.domain.model

import com.rvcoding.imhere.domain.model.UserState.IDLE
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserExposed(
    @Required @SerialName("userId") val id: String,
    @Required val firstName: String = "",
    @Required val lastName: String = "",
    @Required val lastActivity: Long = 0L,
    @Required val state: UserState = IDLE,
    @Required val coordinates: Coordinates = Coordinates(0.0, 0.0, 0L)
) {
    companion object {
        val Default = UserExposed(id = "id")
    }
}
