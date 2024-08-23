package com.rvcoding.imhere.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rvcoding.imhere.domain.models.UserState.IDLE
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class User(
    @PrimaryKey
    val id: String,
    val password: String,
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val lastLogin: Long = 0L,
    val lastActivity: Long = 0L,
    val state: Int = IDLE.state,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val lastCoordinatesTimestamp: Long = 0L
) {
    companion object {
        val Default by lazy { User("default", "default") }
    }
}

@Serializable
data class UserExposed(
    @Required @SerialName("userId") val id: String,
    @Required val firstName: String = "",
    @Required val lastName: String = "",
    @Required val lastActivity: Long = 0L,
    @Required val state: UserState = IDLE,
    @Required val coordinates: Coordinates = Coordinates(0.0, 0.0, 0L)
)

fun User.toExposed() = UserExposed(
    id = id,
    firstName = firstName,
    lastName = lastName,
    lastActivity = lastActivity,
    state = UserState.fromInt(state),
    coordinates = Coordinates(lat, lon, lastCoordinatesTimestamp)
)