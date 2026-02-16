package com.example.workoutmate.data.repository

import android.content.Context
import com.example.workoutmate.data.User
import com.example.workoutmate.data.dao.UserDao
import com.example.workoutmate.data.db.AppDatabase

class UserRepository private constructor(
    private val userDao: UserDao
) {
    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val database = AppDatabase.get(context)
                val instance = UserRepository(database.userDao())
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun usernameExists(username: String): Boolean {
        return userDao.usernameExists(username.trim())
    }

    suspend fun createUser(username: String): Long {
        val user = User(
            username = username
        )
        return userDao.insert(user)
    }
}