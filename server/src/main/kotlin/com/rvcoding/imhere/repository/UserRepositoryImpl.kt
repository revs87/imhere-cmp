package com.rvcoding.imhere.repository

import com.rvcoding.imhere.domain.models.User
import com.rvcoding.imhere.domain.repository.UserRepository
import java.util.Collections

class UserRepositoryImpl: UserRepository {
    private val users: MutableMap<String, User> = Collections.synchronizedMap(mutableMapOf())

    override fun containsEmail(userId: String?): Boolean = userId?.let { users.contains(userId) } ?: false
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
    override fun update(user: User) { users[user.unique()] = user }

    private fun User.unique(): String = this.id
}