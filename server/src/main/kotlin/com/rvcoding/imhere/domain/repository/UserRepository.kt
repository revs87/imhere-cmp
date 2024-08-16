package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.models.User

interface UserRepository {
    fun containsEmail(userId: String?): Boolean
    fun validCredentials(userId: String, password: String): Boolean
    fun get(userId: String): User?
    fun getAll(): List<User>
    fun update(user: User)
}