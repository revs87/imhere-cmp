package com.rvcoding.imhere.domain.data.api.response

import com.rvcoding.imhere.domain.data.db.SessionEntity
import kotlinx.serialization.Serializable

@Serializable
data class SessionsResponse(
    val sessions: List<SessionEntity>
)