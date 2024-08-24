package com.rvcoding.imhere.data.repository

import com.rvcoding.imhere.data.internal.db.SessionsDao
import com.rvcoding.imhere.domain.models.Session
import com.rvcoding.imhere.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SessionsRepositoryImpl(
    private val sessionsDao: SessionsDao
) : SessionRepository {
    private lateinit var sessions: StateFlow<List<Session>>
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            sessions = sessionsDao.getAllSessions().stateIn(this)
        }
    }

    override fun getAllSessions(): List<Session> = sessions.value.sortedByDescending { it.timestamp }
    override fun getUserSessions(userId: String): List<Session> = sessions.value.filter { it.userId == userId }.sortedByDescending { it.timestamp }
    override suspend fun newSession(session: Session) = sessionsDao.insert(session)
}