package com.rvcoding.solotrek.domain.data.api.response

import com.rvcoding.solotrek.domain.data.db.SubscriptionEntity
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionsResponse(
    val subscriptions: List<SubscriptionEntity>
)