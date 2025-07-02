package com.rvcoding.solotrek.domain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rvcoding.solotrek.domain.Configuration.Companion.APP_ID
import com.rvcoding.solotrek.domain.util.sha256
import com.rvcoding.solotrek.domain.util.uniqueRandom
import com.rvcoding.solotrek.util.currentTimeMillis
import kotlinx.serialization.Serializable

@Entity(tableName = "session")
@Serializable
data class SessionEntity(
    @PrimaryKey
    val userId: String,
    val sessionId: String,
    val timestamp: Long = currentTimeMillis()
) {
    companion object {
        fun generate(userId: String): SessionEntity = SessionEntity(userId, "$APP_ID$userId${uniqueRandom()}${currentTimeMillis()}".sha256())
    }
}