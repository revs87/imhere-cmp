package com.rvcoding.imhere.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rvcoding.imhere.domain.Configuration.Companion.APP_ID
import com.rvcoding.imhere.domain.util.sha256
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Session(
    @PrimaryKey
    val userId: String,
    val sessionId: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        fun generate(userId: String): Session = Session(userId, "$APP_ID$userId${System.currentTimeMillis()}".sha256())
    }
}