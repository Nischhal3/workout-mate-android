package com.example.workoutmate.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.workoutmate.data.WorkoutSession
import com.example.workoutmate.data.WorkoutSessionWithExercisesAndSets
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkoutSessionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(session: WorkoutSession): Long

    @Update
    suspend fun update(session: WorkoutSession)

    @Query("SELECT * FROM workout_sessions WHERE userId = :userId ORDER BY date DESC, id DESC")
    fun observeSessionsForUser(userId: Long): Flow<List<WorkoutSession>>

    @Query("DELETE FROM workout_sessions WHERE id = :sessionId")
    suspend fun deleteById(sessionId: Long)

    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId LIMIT 1")
    suspend fun getById(sessionId: Long): WorkoutSession?

    @Query(
        """
        SELECT * FROM workout_sessions
        WHERE userId = :userId
        ORDER BY date DESC, id DESC
    """
    )
    fun observeForUser(userId: Long): Flow<List<WorkoutSession>>

    @Transaction
    @Query(
        """
    SELECT * FROM workout_sessions
    WHERE id = :sessionId AND userId = :userId
"""
    )
    fun observeSessionWithExercisesAndSets(
        userId: Long,
        sessionId: Long
    ): Flow<WorkoutSessionWithExercisesAndSets?>

    @Query(
        """
        SELECT * FROM workout_sessions
        WHERE userId = :userId AND date = :date
        ORDER BY id DESC
    """
    )
    fun observeForUserOnDate(userId: Long, date: LocalDate): Flow<List<WorkoutSession>>

    @Query(
        """
        SELECT * FROM workout_sessions
        WHERE userId = :userId AND date = :date AND title = :title
        LIMIT 1
    """
    )
    suspend fun getByUserDateTitle(userId: Long, date: LocalDate, title: String): WorkoutSession?
}