package com.rvcoding.solotrek.data.internal.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.rvcoding.solotrek.domain.data.db.User
import com.rvcoding.solotrek.util.currentTimeMillis
import kotlinx.coroutines.flow.Flow


@Database(
    entities = [User::class],
    version = 1
)
abstract class UsersDatabase : RoomDatabase() {
    abstract val userDao: UsersDao

    companion object {
        const val DATABASE_NAME = "users_db"
    }
}

@Dao
interface UsersDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUser(userId: String): User?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Transaction
    @Query("UPDATE user SET password = :password WHERE id = :userId")
    suspend fun updatePassword(userId: String, password: String)

    @Query("UPDATE user SET phoneNumber = :phoneNumber WHERE id = :userId")
    suspend fun updatePhoneNumber(userId: String, phoneNumber: String)

    @Query("UPDATE user SET firstName = :firstName WHERE id = :userId")
    suspend fun updateFirstName(userId: String, firstName: String)

    @Query("UPDATE user SET lastName = :lastName WHERE id = :userId")
    suspend fun updateLastName(userId: String, lastName: String)

    @Query("UPDATE user SET lastLogin = :now, lastActivity = :now WHERE id = :userId")
    suspend fun updateLastLogin(userId: String, now: Long = currentTimeMillis())

    @Query("UPDATE user SET lastActivity = :now WHERE id = :userId")
    suspend fun updateLastActivity(userId: String, now: Long = currentTimeMillis())

    @Transaction
    @Query("UPDATE user SET state = :state WHERE id = :userId")
    suspend fun updateState(userId: String, state: Int)

    @Transaction
    @Query("UPDATE user SET lat = :lat, lon = :lon, lastCoordinatesTimestamp = :lastCoordinatesTimestamp WHERE id = :userId")
    suspend fun updateCoordinates(userId: String, lat: Double, lon: Double, lastCoordinatesTimestamp: Long)

    @Transaction
    @Query("DELETE FROM user")
    suspend fun deleteAll()
}