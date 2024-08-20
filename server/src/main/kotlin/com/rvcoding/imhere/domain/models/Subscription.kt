package com.rvcoding.imhere.domain.models

import androidx.room.Entity
import kotlinx.serialization.Serializable


@Entity(primaryKeys = ["userId", "userSubscribedId"])
@Serializable
data class Subscription(
    val userId: String = "",
    val userSubscribedId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)