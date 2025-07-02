package com.rvcoding.solotrek.domain.data.api.response

import com.rvcoding.solotrek.domain.data.db.SessionEntity
import kotlinx.serialization.Serializable

@Serializable
data class SessionsResponse(
    val sessions: List<SessionEntity>
)