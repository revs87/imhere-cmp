package com.rvcoding.imhere.domain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rvcoding.imhere.domain.Configuration.Companion.APP_ID
import com.rvcoding.imhere.domain.util.sha256
import com.rvcoding.imhere.domain.util.uniqueRandom
import kotlinx.serialization.Serializable

@Entity(tableName = "session")
@Serializable
data class SessionEntity(
    @PrimaryKey
    val userId: String,
    val sessionId: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        fun generate(userId: String): SessionEntity = SessionEntity(userId, "$APP_ID$userId${uniqueRandom()}${System.currentTimeMillis()}".sha256())
    }
}