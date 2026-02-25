package com.example.workoutmate.data.repository

import android.content.Context
import androidx.room.withTransaction
import com.example.workoutmate.data.WorkoutExercise
import com.example.workoutmate.data.WorkoutSession
import com.example.workoutmate.data.WorkoutSet
import com.example.workoutmate.data.db.AppDatabase
import java.time.LocalDate

class WorkoutRepository(
    context: Context,
) {
    private val db = AppDatabase.get(context.applicationContext)

    private val setDao = db.workoutSetDao()
    private val sessionDao = db.workoutSessionDao()
    private val exerciseDao = db.workoutExerciseDao()

    // ---------------- READ ----------------
    fun observeSessionsForUser(userId: Long) = sessionDao.observeSessionsForUser(userId)

    fun observeSessionWithExercisesAndSets(userId: Long, sessionId: Long) =
        sessionDao.observeSessionWithExercisesAndSets(userId = userId, sessionId = sessionId)

    // ---------------- CREATE ----------------
    suspend fun createSession(
        userId: Long, title: String, date: LocalDate, notes: String? = null
    ): Long {
        return sessionDao.insert(
            WorkoutSession(
                userId = userId, title = title.trim(), date = date, notes = notes
            )
        )
    }

    suspend fun addExercise(sessionId: Long, name: String, notes: String? = null): Long {
        val nextIndex = (exerciseDao.getMaxOrderIndex(sessionId) ?: -1) + 1
        return exerciseDao.insert(
            WorkoutExercise(
                sessionId = sessionId, name = name.trim(), orderIndex = nextIndex, notes = notes
            )
        )
    }

    suspend fun addSet(exerciseId: Long, weightKg: Double, reps: Int): Long {
        val nextSetNumber = (setDao.getMaxSetNumber(exerciseId) ?: 0) + 1
        return setDao.insert(
            WorkoutSet(
                exerciseId = exerciseId,
                setNumber = nextSetNumber,
                weightKg = weightKg,
                reps = reps,
                completed = false
            )
        )
    }

    // ---------------- UPDATE ----------------
    suspend fun updateExerciseName(exerciseId: Long, newName: String) {
        exerciseDao.updateExerciseName(exerciseId, newName.trim())
    }

    suspend fun toggleSetCompleted(setId: Long, completed: Boolean) {
        setDao.setCompleted(setId, completed)
    }

    suspend fun updateSetValues(setId: Long, weightKg: Double, reps: Int) {
        setDao.updateValues(setId, weightKg, reps)
    }

    suspend fun reorderExercises(orderedExerciseIds: List<Long>) {
        db.withTransaction {
            orderedExerciseIds.forEachIndexed { index, exerciseId ->
                exerciseDao.updateOrderIndex(exerciseId, index)
            }
        }
    }

    suspend fun reorderSets(orderedSetIds: List<Long>) {
        db.withTransaction {
            orderedSetIds.forEachIndexed { idx, setId ->
                setDao.updateSetNumber(setId, 1000 + idx + 1)
            }
            orderedSetIds.forEachIndexed { idx, setId ->
                setDao.updateSetNumber(setId, idx + 1)
            }
        }
    }
}