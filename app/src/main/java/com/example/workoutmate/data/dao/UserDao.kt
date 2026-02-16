package com.example.workoutmate.data.dao

import androidx.room.*
import com.example.workoutmate.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    suspend fun getById(id: Long): User?

    @Delete
    suspend fun delete(user: User)
}
