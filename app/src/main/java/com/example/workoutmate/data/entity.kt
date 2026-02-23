package com.example.workoutmate.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

@Entity(
    tableName = "users", indices = [Index(value = ["username"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val createdAtMs: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "workout_sessions", foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("userId"), Index(value = ["userId", "date", "title"], unique = true)]
)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val title: String,
    val date: LocalDate,
    val notes: String? = null
)

@Entity(
    tableName = "workout_exercises", foreignKeys = [ForeignKey(
        entity = WorkoutSession::class,
        parentColumns = ["id"],
        childColumns = ["sessionId"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("sessionId"), Index(value = ["sessionId", "orderIndex"])]
)
data class WorkoutExercise(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val name: String,
    val orderIndex: Int = 0,
    val notes: String? = null
)

@Entity(
    tableName = "workout_sets", foreignKeys = [ForeignKey(
        entity = WorkoutExercise::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("exerciseId"), Index(value = ["exerciseId", "setNumber"], unique = true)]
)
data class WorkoutSet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val exerciseId: Long,
    val setNumber: Int,
    val weightKg: Double,
    val reps: Int,
    val completed: Boolean = false,
    val createdAtMs: Long = System.currentTimeMillis()
)

/**
 * Relation models for UI: WorkoutSession -> WorkoutExercise -> WorkoutSet
 * (These are NOT tables, just query results.)
 */
data class WorkoutExerciseWithSets(
    @Embedded val exercise: WorkoutExercise, @Relation(
        parentColumn = "id", entityColumn = "exerciseId"
    ) val sets: List<WorkoutSet>
)

data class WorkoutSessionWithExercisesAndSets(
    @Embedded val session: WorkoutSession, @Relation(
        entity = WorkoutExercise::class, parentColumn = "id", entityColumn = "sessionId"
    ) val exercises: List<WorkoutExerciseWithSets>
)