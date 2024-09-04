package com.rvcoding.imhere.domain.model

import com.rvcoding.imhere.util.currentTimeMillis
import kotlinx.serialization.Serializable


@Serializable
data class Subscription(
    val userId: String = "",
    val userSubscribedId: String = "",
    val timestamp: Long = currentTimeMillis()
)