package com.example.workoutmate.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutmate.data.WorkoutSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(set: WorkoutSet): Long

    @Update
    suspend fun update(set: WorkoutSet)

    @Query("DELETE FROM workout_sets WHERE id = :setId")
    suspend fun deleteById(setId: Long)

    @Query(
        """
        SELECT * FROM workout_sets
        WHERE exerciseId = :exerciseId
        ORDER BY setNumber ASC, id ASC
    """
    )
    fun observeForExercise(exerciseId: Long): Flow<List<WorkoutSet>>

    @Query(
        """
        SELECT MAX(setNumber)
        FROM workout_sets
        WHERE exerciseId = :exerciseId
    """
    )
    suspend fun getMaxSetNumber(exerciseId: Long): Int?

    @Query(
        """
        UPDATE workout_sets
        SET completed = :completed
        WHERE id = :setId
    """
    )
    suspend fun setCompleted(setId: Long, completed: Boolean)

    @Query(
        """
        UPDATE workout_sets
        SET weightKg = :weightKg, reps = :reps
        WHERE id = :setId
    """
    )
    suspend fun updateValues(setId: Long, weightKg: Double, reps: Int)

    @Query(
        """
        UPDATE workout_sets
        SET setNumber = :setNumber
        WHERE id = :setId
    """
    )
    suspend fun updateSetNumber(setId: Long, setNumber: Int)
}