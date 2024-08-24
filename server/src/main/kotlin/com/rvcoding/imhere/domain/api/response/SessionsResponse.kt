package com.rvcoding.imhere.domain.api.response

import com.rvcoding.imhere.domain.models.Session
import kotlinx.serialization.Serializable

@Serializable
data class SessionsResponse(
    val sessions: List<Session>
)