package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.data.db.User
import com.rvcoding.imhere.domain.model.Coordinates
import com.rvcoding.imhere.domain.model.UserState

interface ApiUserRepository {
    fun containsUserId(userId: String): Boolean
    fun validCredentials(userId: String, password: String): Boolean
    fun get(userId: String): User?
    fun getAll(): List<User>
    suspend fun insert(user: User)
    suspend fun updateLastLogin(userId: String)
    suspend fun updateLastActivity(userId: String)
    suspend fun updateState(userId: String, state: UserState)
    suspend fun updateCoordinates(userId: String, coordinates: Coordinates)
}