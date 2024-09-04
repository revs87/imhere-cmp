package com.rvcoding.imhere.domain.data.repository

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.data.api.error.DataError
import com.rvcoding.imhere.domain.data.api.response.UsersResponse

interface UsersRepository {
    suspend fun users(): Result<UsersResponse, DataError>
}