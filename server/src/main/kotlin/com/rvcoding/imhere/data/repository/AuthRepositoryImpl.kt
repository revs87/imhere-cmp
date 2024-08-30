package com.rvcoding.imhere.data.repository

import com.rvcoding.imhere.domain.models.User
import com.rvcoding.imhere.domain.repository.AuthRepository
import com.rvcoding.imhere.domain.repository.ChangePasswordResult
import com.rvcoding.imhere.domain.repository.LoginResult
import com.rvcoding.imhere.domain.repository.RegisterResult
import com.rvcoding.imhere.domain.repository.UserRepository
import com.rvcoding.imhere.domain.util.sha256
import com.rvcoding.imhere.model.UserState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthRepositoryImpl(private val userRepository: UserRepository) : AuthRepository {
    private var coScope = CoroutineScope(Dispatchers.IO)

    override fun register(userId: String?, password: String?): RegisterResult {
        return try {
            when {
                !validSemantics(userId, password) -> RegisterResult.InvalidParametersError
                userRepository.containsUserId(userId ?: "") -> RegisterResult.UserAlreadyRegisteredError
                else -> {
                    val user = User(
                        id = userId ?: "",
                        password = password?.sha256() ?: "",
                        lastLogin = System.currentTimeMillis(),
                        lastActivity = System.currentTimeMillis(),
                    )
                    coScope.launch { userRepository.insert(user) }
                    RegisterResult.Success
                }
            }
        } catch (e: Exception) { RegisterResult.UnauthorizedError }
    }

    override fun login(userId: String?, password: String?): LoginResult {
        return try {
            when {
                !validSemantics(userId, password) -> LoginResult.InvalidParametersError
                !userRepository.validCredentials(
                    userId = userId ?: "",
                    password = password?.sha256() ?: ""
                ) -> LoginResult.CredentialsMismatchError
                else -> {
                    val user = userRepository.get(userId ?: "")
                    user?.let {
                        coScope.launch {
                            userRepository.insert(
                                user.copy(
                                    lastLogin = System.currentTimeMillis(),
                                    lastActivity = System.currentTimeMillis(),
                                    state = UserState.IDLE.state
                                )
                            )
                        }
                    }
                    LoginResult.Success
                }
            }
        } catch (e: Exception) { LoginResult.UnauthorizedError }
    }

    override fun changePassword(userId: String?, password: String?, newPassword: String?): ChangePasswordResult {
        return ChangePasswordResult.Success
    }

    private fun validSemantics(userId: String?, password: String?): Boolean = !(userId.isNullOrBlank() || password.isNullOrBlank())
    private fun validPasswordChange(userId: String?, password: String?, newPassword: String?): Boolean = !(userId.isNullOrBlank() || password.isNullOrBlank() || newPassword.isNullOrBlank())
}