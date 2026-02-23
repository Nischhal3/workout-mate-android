package com.example.workoutmate.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.workoutmate.data.WorkoutSessionWithExercisesAndSets
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDetailsDao {

    @Transaction
    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId")
    fun observeSessionWithExercisesAndSets(sessionId: Long): Flow<WorkoutSessionWithExercisesAndSets?>

    @Transaction
    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId")
    suspend fun getSessionWithExercisesAndSets(sessionId: Long): WorkoutSessionWithExercisesAndSets?
}