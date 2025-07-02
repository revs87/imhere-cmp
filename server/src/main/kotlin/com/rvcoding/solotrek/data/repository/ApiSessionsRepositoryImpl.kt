package com.rvcoding.solotrek.data.repository

import com.rvcoding.solotrek.data.internal.db.SessionsDao
import com.rvcoding.solotrek.domain.data.db.SessionEntity
import com.rvcoding.solotrek.domain.repository.ApiSessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ApiSessionsRepositoryImpl(
    private val sessionsDao: SessionsDao
) : ApiSessionRepository {
    private lateinit var sessions: StateFlow<List<SessionEntity>>
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            sessions = sessionsDao.getAllSessions().stateIn(this)
        }
    }

    override fun getAllSessions(): List<SessionEntity> = sessions.value.sortedByDescending { it.timestamp }
    override fun getUserSessions(userId: String): List<SessionEntity> = sessions.value.filter { it.userId == userId }.sortedByDescending { it.timestamp }
    override suspend fun newSession(session: SessionEntity) = sessionsDao.insert(session)
}