package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.models.Subscription

interface SubscriptionRepository {
    fun getAllSubscriptions(): List<Subscription>
    fun getUserSubscriptions(userId: String): List<Subscription>
    suspend fun subscribe(subscription: Subscription)
    suspend fun unsubscribe(userId: String, userSubscribedId: String)
}