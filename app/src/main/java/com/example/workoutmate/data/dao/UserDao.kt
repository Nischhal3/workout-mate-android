package com.example.workoutmate.data.dao

import androidx.room.*
import com.example.workoutmate.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username = :username LIMIT 1)")
    suspend fun usernameExists(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Delete
    suspend fun delete(user: User)
}
