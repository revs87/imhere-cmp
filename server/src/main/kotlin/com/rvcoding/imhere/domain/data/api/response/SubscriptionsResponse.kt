package com.rvcoding.imhere.domain.data.api.response

import com.rvcoding.imhere.domain.data.db.SubscriptionEntity
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionsResponse(
    val subscriptions: List<SubscriptionEntity>
)