package com.example.workoutmate.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

@Entity(
    tableName = "users",
    indices = [Index(value = ["username"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val username: String,
    val createdAtEpochMs: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "workout_days",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index(value = ["userId", "date", "title"])
    ]
)
data class WorkoutDay(
    @PrimaryKey(autoGenerate = true) val workoutDayId: Long = 0,
    val userId: Long,
    val title: String,
    val date: LocalDate,
    val notes: String? = null,
)

@Entity(
    tableName = "exercise_entries",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutDay::class,
            parentColumns = ["workoutDayId"],
            childColumns = ["workoutDayId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("workoutDayId")]
)
data class ExerciseEntry(
    @PrimaryKey(autoGenerate = true) val exerciseEntryId: Long = 0,
    val workoutDayId: Long,
    val name: String,
    val orderIndex: Int = 0,
    val note: String? = null
)

@Entity(
    tableName = "set_entries",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntry::class,
            parentColumns = ["exerciseEntryId"],
            childColumns = ["exerciseEntryId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("exerciseEntryId")]
)
data class SetEntry(
    @PrimaryKey(autoGenerate = true) val setEntryId: Long = 0,
    val exerciseEntryId: Long,
    val setNumber: Int,
    val weightKg: Double,
    val reps: Int,
    val isCompleted: Boolean = true,
    val createdAtEpochMs: Long = System.currentTimeMillis()
)

data class ExerciseWithSets(
    @Embedded val exercise: ExerciseEntry,
    @Relation(
        parentColumn = "exerciseEntryId",
        entityColumn = "exerciseEntryId"
    )
    val sets: List<SetEntry>
)

data class WorkoutDayWithExercisesAndSets(
    @Embedded val workoutDay: WorkoutDay,
    @Relation(
        entity = ExerciseEntry::class,
        parentColumn = "workoutDayId",
        entityColumn = "workoutDayId"
    )
    val exercises: List<ExerciseWithSets>
)