package com.rvcoding.imhere.data.internal.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.rvcoding.imhere.domain.data.db.SessionEntity
import kotlinx.coroutines.flow.Flow


@Database(
    entities = [SessionEntity::class],
    version = 1
)
abstract class SessionsDatabase : RoomDatabase() {
    abstract val sessionDao: SessionsDao

    companion object {
        const val DATABASE_NAME = "session_db"
    }
}

@Dao
interface SessionsDao {
    @Query("SELECT * FROM session")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("SELECT * FROM session WHERE userId = :userId")
    suspend fun getSessions(userId: String): List<SessionEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: SessionEntity)

    @Transaction
    @Query("DELETE FROM session")
    suspend fun deleteAll()
}