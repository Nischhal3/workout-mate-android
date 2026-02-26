package com.example.workoutmate.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutmate.data.User
import com.example.workoutmate.data.WorkoutSession
import com.example.workoutmate.data.WorkoutSessionWithExercisesAndSets
import com.example.workoutmate.data.repository.UserRepository
import com.example.workoutmate.data.repository.WorkoutRepository
import com.example.workoutmate.model.Exercise
import com.example.workoutmate.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val TAG = "UserViewModel"

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val workoutRepository = WorkoutRepository(application)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _sessions = MutableStateFlow<List<WorkoutSession>>(emptyList())
    val sessions: StateFlow<List<WorkoutSession>> = _sessions

    private val _selectedSession = MutableStateFlow<WorkoutSessionWithExercisesAndSets?>(null)

    val selectedSession: StateFlow<WorkoutSessionWithExercisesAndSets?> = _selectedSession

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
        title: String,
        date: LocalDate,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        exercises: List<Exercise>
    ) {
        val userId = currentUser.value?.id ?: return

        val trimmedTitle = title.trim()
        if (trimmedTitle.isEmpty()) return

        viewModelScope.launch {
            try {
                val workoutSessionId = workoutRepository.createSession(userId, trimmedTitle, date)
                if (exercises.isEmpty()) {
                    onSuccess()
                    return@launch
                }

                exercises.forEach { exerciseSet ->
                    val exerciseId = workoutRepository.addExercise(
                        sessionId = workoutSessionId,
                        name = exerciseSet.name.trim(),
                    )

                    exerciseSet.exercises.forEach { entry ->
                        val weight = entry.weight.toDoubleOrNull() ?: return@forEach
                        val reps = entry.reps.toIntOrNull() ?: return@forEach

                        workoutRepository.addSet(
                            reps = reps,
                            weightKg = weight,
                            exerciseId = exerciseId,
                        )
                    }
                }
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
                    _sessions.value = emptyList()
                    return@collectLatest
                }

                workoutRepository.observeSessionsForUser(user.id).collectLatest { sessions ->
                    _sessions.value = sessions
                }
            }
        }
    }

    fun observeSessionWithExercisesAndSets(workoutSessionId: Long) {
        viewModelScope.launch {

            val user = currentUser.value
            if (user == null) {
                _selectedSession.value = null
                return@launch
            }

            workoutRepository.observeSessionWithExercisesAndSets(
                userId = user.id, sessionId = workoutSessionId
            ).distinctUntilChanged().collectLatest { state ->
                _selectedSession.value = state
            }
        }
    }

    fun updateExerciseName(exerciseId: Long, sessionId: Long, newName: String) {
        val trimmed = newName.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            workoutRepository.updateExerciseName(
                exerciseId, sessionId = sessionId, newName = newName
            )
        }
    }

    fun deleteSessionById(
        sessionId: Long, onError: (String) -> Unit, onSuccess: () -> Unit
    ) {
        val userId = currentUser.value?.id
        if (userId == null) {
            onError("No user logged in")
            return
        }

        viewModelScope.launch {
            try {
                val ok = workoutRepository.deleteSessionById(
                    sessionId = sessionId, userId = userId
                )
                if (ok) onSuccess() else onError("Session not found for user ${currentUser.value!!.username}")
            } catch (e: Exception) {
                onError(e.message ?: "Failed to delete session")
            }
        }
    }

    fun deleteExerciseByID(exerciseId: Long, sessionId: Long) {
        viewModelScope.launch {
            workoutRepository.deleteExerciseById(exerciseId = exerciseId, sessionId = sessionId)
        }
    }

    fun clearSelectedWorkoutSession() {
        _selectedSession.value = null
    }
}