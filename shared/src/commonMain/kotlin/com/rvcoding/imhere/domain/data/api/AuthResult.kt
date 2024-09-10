package com.rvcoding.imhere.domain.data.api

import kotlinx.serialization.Serializable


@Serializable
sealed interface AuthResult {
    @Serializable
    sealed class RegisterResult(val code: Int) : AuthResult {
        @Serializable data object Success : RegisterResult(100)
        @Serializable data object InvalidParametersError : RegisterResult(101)
        @Serializable data object CriteriaNotMetError : RegisterResult(102)
        @Serializable data object UserAlreadyRegisteredError : RegisterResult(103)
        @Serializable data object UnauthorizedError : RegisterResult(104)
    }
    @Serializable
    sealed class LoginResult(val code: Int) : AuthResult {
        @Serializable data object Success : LoginResult(200)
        @Serializable data object InvalidParametersError : LoginResult(201)
        @Serializable data object CredentialsMismatchError : LoginResult(202)
        @Serializable data object UnauthorizedError : LoginResult(203)
    }
    @Serializable
    sealed class LogoutResult(val code: Int) : AuthResult {
        @Serializable data object Success : LogoutResult(210)
        @Serializable data object InvalidParametersError : LogoutResult(211)
        @Serializable data object UnauthorizedError : LogoutResult(213)
    }
    @Serializable
    sealed class ChangePasswordResult(val code: Int) : AuthResult {
        @Serializable data object Success : ChangePasswordResult(300)
        @Serializable data object InvalidParametersError : ChangePasswordResult(301)
        @Serializable data object CredentialsMismatchError : ChangePasswordResult(302)
        @Serializable data object CriteriaNotMetError : ChangePasswordResult(303)
    }
    @Serializable
    sealed class KtorStatus : AuthResult {
        @Serializable data object OK : KtorStatus()
        @Serializable data object BadRequest : KtorStatus()
        @Serializable data object Unauthorized : KtorStatus()
        @Serializable data object NotFound : KtorStatus()
        @Serializable data object Forbidden : KtorStatus()
        @Serializable data object InternalServerError : KtorStatus()
    }
}