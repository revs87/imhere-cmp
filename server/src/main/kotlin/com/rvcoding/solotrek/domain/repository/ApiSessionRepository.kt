package com.rvcoding.solotrek.domain.repository

import com.rvcoding.solotrek.domain.data.db.SessionEntity

interface ApiSessionRepository {
    fun getAllSessions(): List<SessionEntity>
    fun getUserSessions(userId: String): List<SessionEntity>
    suspend fun newSession(session: SessionEntity)
}