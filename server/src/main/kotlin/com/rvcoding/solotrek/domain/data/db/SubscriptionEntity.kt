package com.rvcoding.solotrek.domain.data.db

import androidx.room.Entity
import com.rvcoding.solotrek.util.currentTimeMillis
import kotlinx.serialization.Serializable


@Entity(
    tableName = "subscription",
    primaryKeys = ["userId", "userSubscribedId"]
)
@Serializable
data class SubscriptionEntity(
    val userId: String = "",
    val userSubscribedId: String = "",
    val timestamp: Long = currentTimeMillis()
)