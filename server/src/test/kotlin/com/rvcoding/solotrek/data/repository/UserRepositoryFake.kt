package com.rvcoding.solotrek.data.repository

import com.rvcoding.solotrek.domain.data.db.User
import com.rvcoding.solotrek.domain.model.Coordinates
import com.rvcoding.solotrek.domain.model.UserState
import com.rvcoding.solotrek.domain.repository.ApiUserRepository
import java.util.Collections

class UserRepositoryFake : ApiUserRepository {
    private val users: MutableMap<String, User> = Collections.synchronizedMap(mutableMapOf())

    override fun containsUserId(userId: String): Boolean = users.contains(userId)
    override fun validCredentials(userId: String, password: String): Boolean {
        val user = users[userId]
        return when {
            user == null -> false
            user.password == password -> true
            else -> false
        }
    }
    override fun get(userId: String): User? = users[userId]
    override fun getAll(): List<User> = users.values.toList()
    override suspend fun insert(user: User) { users[user.id] = user }
    override suspend fun updateLastLogin(userId: String) {}
    override suspend fun updateLastActivity(userId: String) {}
    override suspend fun updateState(userId: String, state: UserState) {}
    override suspend fun updateCoordinates(userId: String, coordinates: Coordinates) {}
}