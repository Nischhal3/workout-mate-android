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
import com.example.workoutmate.model.UiEvent
import com.example.workoutmate.utils.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _events = MutableSharedFlow<UiEvent>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()


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
        exercises: List<Exercise>,
        onError: (String) -> Unit
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
            } catch (_: Exception) {
                onError("Failed to create workout session.")
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
            try {
                val affectedRows = workoutRepository.updateExerciseName(
                    exerciseId = exerciseId, sessionId = sessionId, newName = trimmed
                )

                if (affectedRows == 0) {
                    _events.tryEmit(UiEvent.ShowError("Exercise not found or session mismatch."))
                }
            } catch (e: Exception) {
                _events.tryEmit(UiEvent.ShowError(e.message ?: "Failed to update exercise name"))
            }
        }
    }

    fun updateSetCompletedStatus(
        setId: Long, exerciseId: Long, completed: Boolean
    ) {
        viewModelScope.launch {
            try {
                val affectedRows = workoutRepository.updateSetCompletedStatus(
                    setId = setId, exerciseId = exerciseId, completed = completed
                )

                if (affectedRows == 0) {
                    _events.tryEmit(UiEvent.ShowError("Set not found or mismatched exercise."))
                }

            } catch (e: Exception) {
                _events.tryEmit(UiEvent.ShowError(e.message ?: "Failed to update set."))
            }
        }
    }

    fun updateSetValues(
        reps: Int, setId: Long, weightKg: Double, exerciseId: Long
    ) {
        viewModelScope.launch {
            try {
                val affectedRows = workoutRepository.updateSetValues(
                    setId = setId, exerciseId = exerciseId, weightKg = weightKg, reps = reps
                )

                if (affectedRows == 0) {
                    _events.tryEmit(UiEvent.ShowError("Set not found or mismatched exercise."))
                }

            } catch (e: Exception) {
                _events.tryEmit(UiEvent.ShowError(e.message ?: "Failed to update set."))
            }
        }
    }

    fun deleteSessionById(
        sessionId: Long, title: String, onResult: (success: Boolean) -> Unit
    ) {
        val userId = currentUser.value?.id
        if (userId == null) {
            _events.tryEmit(UiEvent.ShowError("No user logged in"))
            onResult(false)
            return
        }

        viewModelScope.launch {
            try {
                val deleted = workoutRepository.deleteSessionById(
                    sessionId = sessionId, userId = userId
                )
                if (deleted) {
                    onResult(true)
                    _events.tryEmit(
                        UiEvent.ShowSuccess("Deleted workout session $title.")
                    )
                } else {
                    _events.tryEmit(
                        UiEvent.ShowError("Session not found for user ${currentUser.value!!.username}")
                    )
                    onResult(false)
                }
            } catch (e: Exception) {
                _events.tryEmit(UiEvent.ShowError(e.message ?: "Failed to delete session"))
                onResult(false)
            }
        }
    }

    fun deleteExerciseByID(
        exerciseId: Long, sessionId: Long
    ) {
        viewModelScope.launch {
            try {
                val affectedRows = workoutRepository.deleteExerciseById(
                    exerciseId = exerciseId, sessionId = sessionId
                )

                if (affectedRows > 0) {
                    _events.tryEmit(
                        UiEvent.ShowSuccess("Exercise deleted successfully.")
                    )
                } else {
                    _events.tryEmit(
                        UiEvent.ShowError("Exercise not found or mismatched session.")
                    )
                }

            } catch (e: Exception) {
                _events.tryEmit(
                    UiEvent.ShowError(e.message ?: "Failed to delete exercise.")
                )
            }
        }
    }

    fun clearSelectedWorkoutSession() {
        _selectedSession.value = null
    }
}