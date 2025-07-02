package com.rvcoding.solotrek.data.internal.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.rvcoding.solotrek.domain.data.db.SubscriptionEntity
import kotlinx.coroutines.flow.Flow


@Database(
    entities = [SubscriptionEntity::class],
    version = 1
)
abstract class SubscriptionsDatabase : RoomDatabase() {
    abstract val subscriptionDao: SubscriptionsDao

    companion object {
        const val DATABASE_NAME = "subscriptions_db"
    }
}

@Dao
interface SubscriptionsDao {
    @Query("SELECT * FROM subscription")
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subscription: SubscriptionEntity)

    @Transaction
    @Query("DELETE FROM subscription WHERE userId = :userId AND userSubscribedId = :userSubscribedId")
    suspend fun delete(userId: String, userSubscribedId: String)

    @Transaction
    @Query("DELETE FROM subscription")
    suspend fun deleteAll()
}