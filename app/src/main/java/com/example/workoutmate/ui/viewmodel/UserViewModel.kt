package com.example.workoutmate.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutmate.data.User
import com.example.workoutmate.data.repository.UserRepository
import com.example.workoutmate.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val workoutRepository = WorkoutRepository(application)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun createUser(
        username: String, onError: (String) -> Unit, onSuccess: () -> Unit
    ) {
        val trimmed = username.trim()
        if (trimmed.isEmpty()) {
            onError("Username cannot be empty")
            return
        }

        viewModelScope.launch {
            val existingUser = userRepository.getUserByUsername(trimmed)
            if (existingUser != null) {
                onError("User already exists")
            } else {
                userRepository.createUser(trimmed)
                onSuccess()
            }
        }
    }

    fun loginByUsername(
        username: String, onError: (String) -> Unit, onSuccess: () -> Unit
    ) {
        val trimmed = username.trim()
        if (trimmed.isEmpty()) {
            onError("Enter username")
            return
        }

        viewModelScope.launch {
            val user = userRepository.getUserByUsername(trimmed)

            if (user != null) {
                _currentUser.value = user
                onSuccess()
            } else {
                onError("User not found. Create user first.")
            }
        }
    }
}