package com.rvcoding.solotrek.data.repository

import com.rvcoding.solotrek.data.internal.db.SubscriptionsDao
import com.rvcoding.solotrek.domain.data.db.SubscriptionEntity
import com.rvcoding.solotrek.domain.repository.ApiSubscriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ApiSubscriptionsRepositoryImpl(
    private val subscriptionsDao: SubscriptionsDao
) : ApiSubscriptionRepository {
    private lateinit var subscriptions: StateFlow<List<SubscriptionEntity>>
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            subscriptions = subscriptionsDao.getAllSubscriptions().stateIn(this)
        }
    }

    override fun getAllSubscriptions(): List<SubscriptionEntity> = subscriptions.value.sortedByDescending { it.timestamp }
    override fun getUserSubscriptions(userId: String): List<SubscriptionEntity> = subscriptions.value.filter { it.userId == userId }.sortedByDescending { it.timestamp }
    override fun getUserSubscribers(userId: String): List<SubscriptionEntity> = subscriptions.value.filter { it.userSubscribedId == userId }.sortedByDescending { it.timestamp }
    override suspend fun subscribe(subscription: SubscriptionEntity) = subscriptionsDao.insert(subscription)
    override suspend fun unsubscribe(userId: String, userSubscribedId: String) = subscriptionsDao.delete(userId, userSubscribedId)
}