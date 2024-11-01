package com.rvcoding.imhere.domain.data.repository

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.api.error.HttpError
import com.rvcoding.imhere.domain.data.api.response.SubscriptionsResponse
import com.rvcoding.imhere.domain.data.api.response.UsersResponse
import com.rvcoding.imhere.domain.model.Subscription
import com.rvcoding.imhere.domain.model.User
import com.rvcoding.imhere.domain.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


open class UsersRepositoryApiOnlyImpl(private val api: IHApi) : UsersRepository {
    protected val _users = MutableStateFlow(emptyList<User>())
    override val users : StateFlow<List<User>> = _users.asStateFlow()
    protected val _subscribedUsers = MutableStateFlow(emptyList<User>())
    override val subscribedUsers : StateFlow<List<User>> = _subscribedUsers.asStateFlow()
    protected val _subscribingUsers = MutableStateFlow(emptyList<User>())
    override val subscribingUsers : StateFlow<List<User>> = _subscribingUsers.asStateFlow()

    protected val _subscriptions = MutableStateFlow(emptyList<Subscription>())
    protected val subscriptions : StateFlow<List<Subscription>> = _subscriptions.asStateFlow()

    override suspend fun users(userId: String): Result<UsersResponse, HttpError> {
        api.users().let { result ->
            return when (result) {
                is Result.Success -> {
                    _users.update { result.data.users.filter { user -> user.id != userId } }
                    Result.Success(UsersResponse(result.data.users.filter { user -> user.id != userId }))
                }
                is Result.Error -> Result.Error(result.error)
            }
        }
    }

    override suspend fun userSubscriptions(userId: String): Result<SubscriptionsResponse, HttpError> {
        return when (val result = api.subscriptionsFromUser(userId)) {
            is Result.Success -> {
                _subscriptions.update { result.data.subscriptions }
                Result.Success(result.data)
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun usersSubscribed(userId: String): Result<UsersResponse, HttpError> {
        return when (val result = api.userSubscribedUsers(userId)) {
            is Result.Success -> {
                _subscribedUsers.update { result.data.users }
                Result.Success(result.data)
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun usersSubscribing(userId: String): Result<UsersResponse, HttpError> {
        return when (val result = api.userSubscribers(userId)) {
            is Result.Success -> {
                _subscribingUsers.update { result.data.users }
                Result.Success(result.data)
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun subscribe(userId: String, userIdToSubscribe: String): Result<Unit, HttpError> {
        return when (val result = api.subscribe(userId, userIdToSubscribe)) {
            is Result.Success -> {
                _subscribedUsers.update { users -> users + User(id = userIdToSubscribe) }
                Result.Success(Unit)
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun unsubscribe(userId: String, userIdToUnsubscribe: String): Result<Unit, HttpError> {
        return when (val result = api.unsubscribe(userId, userIdToUnsubscribe)) {
            is Result.Success -> {
                _subscribedUsers.update { users -> users - User(id = userIdToUnsubscribe) }
                Result.Success(Unit)
            }
            is Result.Error -> Result.Error(result.error)
        }
    }
}