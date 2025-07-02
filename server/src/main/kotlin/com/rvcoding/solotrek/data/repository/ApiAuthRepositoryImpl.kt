package com.rvcoding.solotrek.data.repository

import com.rvcoding.solotrek.domain.data.api.AuthResult.ChangePasswordResult
import com.rvcoding.solotrek.domain.data.api.AuthResult.LoginResult
import com.rvcoding.solotrek.domain.data.api.AuthResult.LogoutResult
import com.rvcoding.solotrek.domain.data.api.AuthResult.RegisterResult
import com.rvcoding.solotrek.domain.data.db.User
import com.rvcoding.solotrek.domain.model.UserState
import com.rvcoding.solotrek.domain.repository.ApiAuthRepository
import com.rvcoding.solotrek.domain.repository.ApiUserRepository
import com.rvcoding.solotrek.domain.util.sha256
import com.rvcoding.solotrek.util.currentTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ApiAuthRepositoryImpl(private val userRepository: ApiUserRepository) : ApiAuthRepository {
    private var coScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun register(userId: String?, password: String?, firstName: String, lastName: String): RegisterResult {
        return try {
            when {
                !validSemantics(userId, password) -> RegisterResult.InvalidParametersError
                userRepository.containsUserId(userId ?: "") -> RegisterResult.UserAlreadyRegisteredError
                else -> {
                    val user = User(
                        id = userId ?: "",
                        password = password?.sha256() ?: "",
                        firstName = firstName,
                        lastName = lastName,
                        lastLogin = currentTimeMillis(),
                        lastActivity = currentTimeMillis(),
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
                                    lastLogin = currentTimeMillis(),
                                    lastActivity = currentTimeMillis(),
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

    override fun logout(userId: String?): LogoutResult {
        return try {
            when {
                 userId.isNullOrBlank() -> LogoutResult.InvalidParametersError
                 else -> {
                    val user = userRepository.get(userId)
                    user?.let {
                        coScope.launch {
                            userRepository.insert(
                                user.copy(
                                    lastActivity = currentTimeMillis(),
                                    state = UserState.LOGGED_OUT.state
                                )
                            )
                        }
                    }
                    LogoutResult.Success
                }
            }
        } catch (e: Exception) { LogoutResult.UnauthorizedError }
    }

    override fun changePassword(userId: String?, password: String?, newPassword: String?): ChangePasswordResult {
        return ChangePasswordResult.Success
    }

    private fun validSemantics(userId: String?, password: String?): Boolean = !(userId.isNullOrBlank() || password.isNullOrBlank())
    private fun validPasswordChange(userId: String?, password: String?, newPassword: String?): Boolean = !(userId.isNullOrBlank() || password.isNullOrBlank() || newPassword.isNullOrBlank())
}