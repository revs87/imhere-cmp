package com.rvcoding.imhere.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val password: String,
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val lastLogin: Long = 0L
)