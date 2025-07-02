package com.rvcoding.solotrek.domain.model

import com.rvcoding.solotrek.util.currentTimeMillis
import kotlinx.serialization.Serializable


@Serializable
data class Subscription(
    val userId: String = "",
    val userSubscribedId: String = "",
    val timestamp: Long = currentTimeMillis()
)