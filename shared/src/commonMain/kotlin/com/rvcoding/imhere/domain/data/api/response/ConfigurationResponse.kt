package com.rvcoding.imhere.domain.data.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    val configuration: Map<String, String>
)