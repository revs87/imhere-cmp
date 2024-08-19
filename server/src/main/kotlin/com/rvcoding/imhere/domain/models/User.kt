package com.rvcoding.imhere.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rvcoding.imhere.domain.models.UserState.OFFLINE
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
    val state: Int = OFFLINE.state
) {
    companion object {
        val Default by lazy { User("default", "default") }
    }
}