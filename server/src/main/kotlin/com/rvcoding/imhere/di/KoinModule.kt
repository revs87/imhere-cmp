package com.rvcoding.imhere.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.rvcoding.imhere.data.internal.db.SessionsDao
import com.rvcoding.imhere.data.internal.db.SessionsDatabase
import com.rvcoding.imhere.data.internal.db.SubscriptionsDao
import com.rvcoding.imhere.data.internal.db.SubscriptionsDatabase
import com.rvcoding.imhere.data.internal.db.UsersDao
import com.rvcoding.imhere.data.internal.db.UsersDatabase
import com.rvcoding.imhere.data.repository.AuthRepositoryImpl
import com.rvcoding.imhere.data.repository.SessionsRepositoryImpl
import com.rvcoding.imhere.data.repository.SubscriptionsRepositoryImpl
import com.rvcoding.imhere.data.repository.UserRepositoryImpl
import com.rvcoding.imhere.domain.repository.AuthRepository
import com.rvcoding.imhere.domain.repository.SessionRepository
import com.rvcoding.imhere.domain.repository.SubscriptionRepository
import com.rvcoding.imhere.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val koinModule = module {
    single<SessionsDao> {
        Room.databaseBuilder<SessionsDatabase>(
            name = SessionsDatabase.DATABASE_NAME
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
            .sessionDao
    }
    single<UsersDao> {
        Room.databaseBuilder<UsersDatabase>(
            name = UsersDatabase.DATABASE_NAME
        )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
        .userDao
    }
    single<SubscriptionsDao> {
        Room.databaseBuilder<SubscriptionsDatabase>(
            name = SubscriptionsDatabase.DATABASE_NAME
        )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
        .subscriptionDao
    }
    single<SessionRepository> { SessionsRepositoryImpl(get()) }
    single<SubscriptionRepository> { SubscriptionsRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}