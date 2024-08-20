package com.rvcoding.imhere.domain.api.response

import com.rvcoding.imhere.domain.models.Subscription
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionsResponse(
    val subscriptions: List<Subscription>
)