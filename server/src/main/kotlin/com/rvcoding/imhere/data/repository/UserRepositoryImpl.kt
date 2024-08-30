package com.rvcoding.imhere.data.repository

import com.rvcoding.imhere.data.internal.db.UsersDao
import com.rvcoding.imhere.model.Coordinates
import com.rvcoding.imhere.domain.models.User
import com.rvcoding.imhere.model.UserState
import com.rvcoding.imhere.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserRepositoryImpl(private val usersDao: UsersDao) : UserRepository {
    private lateinit var users: StateFlow<List<User>>
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            users = usersDao.getAllUsers().stateIn(this)
        }
    }
    private fun List<User>.find(userId: String): User? = this.find { it.id == userId }

    override fun containsUserId(userId: String): Boolean = users.value.find(userId) != null
    override fun validCredentials(userId: String, password: String): Boolean {
        val user = users.value.find(userId)
        return when {
            user == null -> false
            user.password == password -> true
            else -> false
        }
    }
    override fun get(userId: String): User? = users.value.find(userId)
    override fun getAll(): List<User> = users.value.toList()
    override suspend fun insert(user: User) {
        if (containsUserId(user.id)) {
            usersDao.updatePassword(userId = user.id, password = user.password)
            usersDao.updatePhoneNumber(userId = user.id, phoneNumber = user.phoneNumber)
            usersDao.updateFirstName(userId = user.id, firstName = user.firstName)
            usersDao.updateLastName(userId = user.id, lastName = user.lastName)
        } else {
            usersDao.insert(user)
        }
    }
    override suspend fun updateLastLogin(userId: String) = usersDao.updateLastLogin(userId = userId)
    override suspend fun updateLastActivity(userId: String) = usersDao.updateLastActivity(userId = userId)
    override suspend fun updateState(userId: String, state: UserState) = usersDao.updateState(userId = userId, state = state.state)
    override suspend fun updateCoordinates(userId: String, coordinates: Coordinates) = usersDao.updateCoordinates(userId = userId, lat = coordinates.lat, lon = coordinates.lon, lastCoordinatesTimestamp = coordinates.timestamp)

}