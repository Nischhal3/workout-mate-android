package com.example.workoutmate.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workoutmate.data.User
import com.example.workoutmate.data.WorkoutExercise
import com.example.workoutmate.data.WorkoutSession
import com.example.workoutmate.data.WorkoutSet
import com.example.workoutmate.data.dao.UserDao
import com.example.workoutmate.data.dao.WorkoutDetailsDao
import com.example.workoutmate.data.dao.WorkoutExerciseDao
import com.example.workoutmate.data.dao.WorkoutSessionDao
import com.example.workoutmate.data.dao.WorkoutSetDao

@Database(
    entities = [User::class, WorkoutSet::class, WorkoutSession::class, WorkoutExercise::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun workoutSetDao(): WorkoutSetDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun workoutDetailsDao(): WorkoutDetailsDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "workout_mate.db"
            ).fallbackToDestructiveMigration(true) // OK for early dev/testing
                .build().also { INSTANCE = it }
        }
    }
}