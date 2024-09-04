package com.rvcoding.imhere.domain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rvcoding.imhere.domain.model.Coordinates
import com.rvcoding.imhere.domain.model.UserExposed
import com.rvcoding.imhere.domain.model.UserState
import com.rvcoding.imhere.domain.model.UserState.IDLE
import kotlinx.serialization.Serializable


typealias User = UserEntity

@Entity(tableName = "user")
@Serializable
data class UserEntity(
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
        val Default by lazy { UserEntity("default", "default") }
    }
}

fun UserEntity.toExposed() = UserExposed(
    id = id,
    firstName = firstName,
    lastName = lastName,
    lastActivity = lastActivity,
    state = UserState.fromInt(state),
    coordinates = Coordinates(lat, lon, lastCoordinatesTimestamp)
)