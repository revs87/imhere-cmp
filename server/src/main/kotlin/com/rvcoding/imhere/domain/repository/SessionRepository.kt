package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.models.Session

interface SessionRepository {
    fun getAllSessions(): List<Session>
    fun getUserSessions(userId: String): List<Session>
    suspend fun newSession(session: Session)
}