package com.example.workoutmate.data.dao

import androidx.room.*
import com.example.workoutmate.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Delete
    suspend fun delete(user: User)
}
