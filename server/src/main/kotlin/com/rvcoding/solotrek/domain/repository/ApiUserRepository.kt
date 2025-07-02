package com.rvcoding.solotrek.domain.repository

import com.rvcoding.solotrek.domain.data.db.User
import com.rvcoding.solotrek.domain.model.Coordinates
import com.rvcoding.solotrek.domain.model.UserState

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