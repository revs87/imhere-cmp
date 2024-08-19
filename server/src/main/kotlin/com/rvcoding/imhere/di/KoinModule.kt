package com.rvcoding.imhere.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.rvcoding.imhere.data.internal.db.UsersDao
import com.rvcoding.imhere.data.internal.db.UsersDatabase
import com.rvcoding.imhere.data.repository.AuthRepositoryImpl
import com.rvcoding.imhere.data.repository.UserRepositoryImpl
import com.rvcoding.imhere.domain.repository.AuthRepository
import com.rvcoding.imhere.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val koinModule = module {
    single<UsersDao> {
        Room.databaseBuilder<UsersDatabase>(
            name = UsersDatabase.DATABASE_NAME
        )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
        .userDao
    }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}