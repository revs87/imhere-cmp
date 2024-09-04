package com.rvcoding.imhere.domain.data.db

import androidx.room.Entity
import kotlinx.serialization.Serializable


@Entity(
    tableName = "subscription",
    primaryKeys = ["userId", "userSubscribedId"]
)
@Serializable
data class SubscriptionEntity(
    val userId: String = "",
    val userSubscribedId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)