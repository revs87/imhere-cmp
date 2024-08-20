package com.rvcoding.imhere.data.repository

import com.rvcoding.imhere.data.internal.db.SubscriptionsDao
import com.rvcoding.imhere.domain.models.Subscription
import com.rvcoding.imhere.domain.repository.SubscriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SubscriptionsRepositoryImpl(
    private val subscriptionsDao: SubscriptionsDao
) : SubscriptionRepository {
    private lateinit var subscriptions: StateFlow<List<Subscription>>
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            subscriptions = subscriptionsDao.getAllSubscriptions().stateIn(this)
        }
    }

    override fun getAllSubscriptions(): List<Subscription> = subscriptions.value
    override fun getUserSubscriptions(userId: String): List<Subscription> = subscriptions.value.filter { it.userId == userId }.sortedByDescending { it.timestamp }
    override suspend fun subscribe(subscription: Subscription) = subscriptionsDao.insert(subscription)
    override suspend fun unsubscribe(userId: String, userSubscribedId: String) = subscriptionsDao.delete(userId, userSubscribedId)
}