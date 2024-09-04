package com.rvcoding.imhere.domain.data.repository

import com.rvcoding.imhere.api.response.UsersResponse
import com.rvcoding.imhere.domain.DataError
import com.rvcoding.imhere.domain.Result

interface UsersRepository {
    suspend fun users(): Result<UsersResponse, DataError>
}