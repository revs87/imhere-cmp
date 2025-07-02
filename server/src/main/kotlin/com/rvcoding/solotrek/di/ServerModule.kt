package com.rvcoding.solotrek.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.rvcoding.solotrek.data.internal.db.SessionsDao
import com.rvcoding.solotrek.data.internal.db.SessionsDatabase
import com.rvcoding.solotrek.data.internal.db.SubscriptionsDao
import com.rvcoding.solotrek.data.internal.db.SubscriptionsDatabase
import com.rvcoding.solotrek.data.internal.db.UsersDao
import com.rvcoding.solotrek.data.internal.db.UsersDatabase
import com.rvcoding.solotrek.data.repository.ApiAuthRepositoryImpl
import com.rvcoding.solotrek.data.repository.ApiSessionsRepositoryImpl
import com.rvcoding.solotrek.data.repository.ApiSubscriptionsRepositoryImpl
import com.rvcoding.solotrek.data.repository.ApiUserRepositoryImpl
import com.rvcoding.solotrek.domain.repository.ApiAuthRepository
import com.rvcoding.solotrek.domain.repository.ApiSessionRepository
import com.rvcoding.solotrek.domain.repository.ApiSubscriptionRepository
import com.rvcoding.solotrek.domain.repository.ApiUserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val serverModule = module(createdAtStart = true) {
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
    single<ApiSessionRepository> { ApiSessionsRepositoryImpl(get()) }
    single<ApiAuthRepository> { ApiAuthRepositoryImpl(get()) }
    single<ApiUserRepository> { ApiUserRepositoryImpl(get()) }


    single<ApiSubscriptionRepository> { ApiSubscriptionsRepositoryImpl(get()) }
}