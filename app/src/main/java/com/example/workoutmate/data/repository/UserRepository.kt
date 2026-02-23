package com.example.workoutmate.data.repository

import android.content.Context
import com.example.workoutmate.data.User
import com.example.workoutmate.data.db.AppDatabase

class UserRepository(
    context: Context,
) {
    private val userDao = AppDatabase.get(context.applicationContext).userDao()

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