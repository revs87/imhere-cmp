package com.rvcoding.imhere.data.repository

import com.rvcoding.imhere.domain.Result
import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.api.error.DataError
import com.rvcoding.imhere.domain.data.api.response.UsersResponse
import com.rvcoding.imhere.domain.data.repository.UsersRepository

class UsersRepositoryImpl(
    private val api: IHApi
) : UsersRepository {
    override suspend fun users(): Result<UsersResponse, DataError> {
        return try {
            when (val response = api.users()) {
                is Result.Success -> Result.Success(response.data)
                is Result.Error -> Result.Error(DataError.Message("Error fetching users"))
            }
        } catch (e: Exception) {
            Result.Error(DataError.Message("Error fetching users"))
        }
    }
}