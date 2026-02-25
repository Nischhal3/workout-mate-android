package com.example.workoutmate.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutmate.data.WorkoutExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(exercise: WorkoutExercise): Long

    @Update
    suspend fun update(exercise: WorkoutExercise)

    @Query("UPDATE workout_exercises SET name = :newName WHERE id = :exerciseId")
    suspend fun updateExerciseName(exerciseId: Long, newName: String): Int

    @Query("DELETE FROM workout_exercises WHERE id = :exerciseId AND sessionId = :sessionId")
    suspend fun deleteExerciseById(exerciseId: Long, sessionId: Long)

    @Query(
        """
        SELECT * FROM workout_exercises
        WHERE sessionId = :sessionId
        ORDER BY orderIndex ASC, id ASC
    """
    )
    fun observeForSession(sessionId: Long): Flow<List<WorkoutExercise>>

    @Query(
        """
        SELECT MAX(orderIndex)
        FROM workout_exercises
        WHERE sessionId = :sessionId
    """
    )
    suspend fun getMaxOrderIndex(sessionId: Long): Int?

    @Query(
        """
        UPDATE workout_exercises
        SET orderIndex = :orderIndex
        WHERE id = :exerciseId
    """
    )
    suspend fun updateOrderIndex(exerciseId: Long, orderIndex: Int)
}