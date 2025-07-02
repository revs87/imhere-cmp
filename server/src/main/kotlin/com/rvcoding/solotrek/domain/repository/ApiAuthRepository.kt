package com.rvcoding.solotrek.domain.repository

import com.rvcoding.solotrek.domain.data.api.AuthResult

interface ApiAuthRepository {
    fun register(userId: String?, password: String?, firstName: String = "", lastName: String = ""): AuthResult.RegisterResult
    fun login(userId: String?, password: String?): AuthResult.LoginResult
    fun logout(userId: String?): AuthResult.LogoutResult
    fun changePassword(userId: String?, password: String?, newPassword: String?): AuthResult.ChangePasswordResult
}
