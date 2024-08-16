package com.rvcoding.imhere.repository

import com.rvcoding.imhere.domain.models.User
import com.rvcoding.imhere.domain.repository.AuthRepository
import com.rvcoding.imhere.domain.repository.ChangePasswordResult
import com.rvcoding.imhere.domain.repository.LoginResult
import com.rvcoding.imhere.domain.repository.RegisterResult
import com.rvcoding.imhere.domain.repository.UserRepository
import com.rvcoding.imhere.domain.util.sha256
import org.koin.java.KoinJavaComponent.inject
import java.util.Date

class AuthRepositoryImpl : AuthRepository {
    private val userRepository: UserRepository by inject(UserRepository::class.java)

    override fun register(userId: String?, password: String?): RegisterResult {
        return try {
            when {
                !validCredentials(userId, password) -> RegisterResult.InvalidParametersError
                userRepository.containsEmail(userId) -> RegisterResult.UserAlreadyRegisteredError
                else -> {
                    val user = User(
                        id = userId ?: "",
                        password = password?.sha256() ?: "",
                        lastLogin = Date().time,
                    )
                    userRepository.update(user)
                    RegisterResult.Success
                }
            }
        } catch (e: Exception) { RegisterResult.UnauthorizedError }
    }

    override fun login(userId: String?, password: String?): LoginResult {
        return try {
            when {
                !validCredentials(userId, password) -> LoginResult.InvalidParametersError
                !userRepository.validCredentials(userId ?: "", password?.sha256() ?: "") -> LoginResult.CredentialsMismatchError
                else -> {
                    val user = userRepository.get(userId ?: "")
                    user?.let {
                        userRepository.update(
                            user.copy(lastLogin = Date().time)
                        )
                    }
                    LoginResult.Success
                }
            }
        } catch (e: Exception) { LoginResult.UnauthorizedError }
    }

    override fun changePassword(userId: String?, password: String?, newPassword: String?): ChangePasswordResult {
        return ChangePasswordResult.Success
    }

    private fun validCredentials(userId: String?, password: String?): Boolean = !(userId.isNullOrBlank() || password.isNullOrBlank())
    private fun validPasswordChange(userId: String?, password: String?, newPassword: String?): Boolean = !(userId.isNullOrBlank() || password.isNullOrBlank() || newPassword.isNullOrBlank())
}