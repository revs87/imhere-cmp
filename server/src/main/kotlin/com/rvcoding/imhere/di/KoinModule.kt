package com.rvcoding.imhere.di

import com.rvcoding.imhere.domain.repository.AuthRepository
import com.rvcoding.imhere.domain.repository.UserRepository
import com.rvcoding.imhere.repository.AuthRepositoryImpl
import com.rvcoding.imhere.repository.UserRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
}