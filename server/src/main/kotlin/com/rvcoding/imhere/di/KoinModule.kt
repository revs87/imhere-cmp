package com.rvcoding.imhere.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.rvcoding.imhere.data.internal.db.SessionsDao
import com.rvcoding.imhere.data.internal.db.SessionsDatabase
import com.rvcoding.imhere.data.internal.db.SubscriptionsDao
import com.rvcoding.imhere.data.internal.db.SubscriptionsDatabase
import com.rvcoding.imhere.data.internal.db.UsersDao
import com.rvcoding.imhere.data.internal.db.UsersDatabase
import com.rvcoding.imhere.data.repository.ApiAuthRepositoryImpl
import com.rvcoding.imhere.data.repository.ApiSessionsRepositoryImpl
import com.rvcoding.imhere.data.repository.ApiUserRepositoryImpl
import com.rvcoding.imhere.data.repository.SubscriptionsRepositoryImpl
import com.rvcoding.imhere.domain.repository.ApiAuthRepository
import com.rvcoding.imhere.domain.repository.ApiSessionRepository
import com.rvcoding.imhere.domain.repository.ApiSubscriptionRepository
import com.rvcoding.imhere.domain.repository.ApiUserRepository
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
    single<ApiSessionRepository> { ApiSessionsRepositoryImpl(get()) }
    single<ApiAuthRepository> { ApiAuthRepositoryImpl(get()) }
    single<ApiUserRepository> { ApiUserRepositoryImpl(get()) }


    single<ApiSubscriptionRepository> { SubscriptionsRepositoryImpl(get()) }
}