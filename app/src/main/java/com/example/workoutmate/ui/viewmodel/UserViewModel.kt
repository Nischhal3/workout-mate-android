package com.example.workoutmate.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutmate.data.User
import com.example.workoutmate.data.WorkoutSession
import com.example.workoutmate.data.repository.UserRepository
import com.example.workoutmate.data.repository.WorkoutRepository
import com.example.workoutmate.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val TAG = "UserViewModel"

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val workoutRepository = WorkoutRepository(application)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _workoutSessions = MutableStateFlow<List<WorkoutSession>>(emptyList())
    val workoutSessions: StateFlow<List<WorkoutSession>> = _workoutSessions

    init {
        observeWorkoutSessions()
    }

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
                Logger(TAG, "Created user with username: $username.")
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

    fun addWorkoutSession(
        title: String, date: LocalDate, onError: (String) -> Unit, onSuccess: () -> Unit
    ) {
        val userId = currentUser.value?.id ?: return

        val trimmedTitle = title.trim()
        if (trimmedTitle.isEmpty()) return

        viewModelScope.launch {
            try {
                workoutRepository.createSession(userId, trimmedTitle, date)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to save workout")
            }
        }
    }

    private fun observeWorkoutSessions() {
        viewModelScope.launch {
            currentUser.collectLatest { user ->
                if (user == null) {
                    _workoutSessions.value = emptyList()
                    return@collectLatest
                }

                workoutRepository.observeSessionsForUser(user.id).collectLatest { sessions ->
                    _workoutSessions.value = sessions
                }
            }
        }
    }
}