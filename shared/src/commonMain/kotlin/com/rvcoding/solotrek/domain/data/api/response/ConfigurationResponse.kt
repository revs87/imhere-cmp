package com.rvcoding.solotrek.domain.data.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    val configuration: Map<String, String>
)