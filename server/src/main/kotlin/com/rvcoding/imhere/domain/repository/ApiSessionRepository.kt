package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.data.db.SessionEntity

interface ApiSessionRepository {
    fun getAllSessions(): List<SessionEntity>
    fun getUserSessions(userId: String): List<SessionEntity>
    suspend fun newSession(session: SessionEntity)
}