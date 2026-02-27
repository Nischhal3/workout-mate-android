package com.example.workoutmate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workoutmate.model.Exercise
import com.example.workoutmate.model.SetEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "WorkoutEditorViewModel"

class WorkoutEditorViewModel : ViewModel() {

    private val _workoutTitle = MutableStateFlow("")
    val workoutTitle: StateFlow<String> = _workoutTitle.asStateFlow()

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> = _showAddDialog.asStateFlow()

    fun setWorkoutTitle(title: String) {
        _workoutTitle.value = title
    }

    fun setShowAddDialog(show: Boolean) {
        _showAddDialog.value = show
    }

    fun addExerciseSet(exercise: Exercise) {
        _exercises.update { it + exercise }
        _showAddDialog.value = false
    }

    fun updateExerciseName(exerciseIndex: Int, newName: String) {
        _exercises.update { list ->
            if (exerciseIndex !in list.indices) return@update list

            list.toMutableList().also {
                val old = it[exerciseIndex]
                it[exerciseIndex] = old.copy(name = newName)
            }
        }
    }

    fun deleteExercise(exerciseIndex: Int) {
        _exercises.update { list ->
            if (exerciseIndex !in list.indices) return@update list
            list.toMutableList().also { it.removeAt(exerciseIndex) }
        }
    }

    fun updateSet(
        setIndex: Int,
        exerciseIndex: Int,
        newEntry: SetEntry
    ) {
        val weight = newEntry.weight.toDoubleOrNull()
        val reps = newEntry.reps.toIntOrNull()

        if (weight == null || reps == null) return

        _exercises.update { list ->
            val exercise = list.getOrNull(exerciseIndex) ?: return@update list
            val sets = exercise.exercises.toMutableList()

            if (setIndex !in sets.indices) return@update list

            sets[setIndex] = newEntry

            list.toMutableList().apply {
                this[exerciseIndex] = exercise.copy(exercises = sets)
            }
        }
    }

    fun deleteSet(exerciseIndex: Int, setIndex: Int) {
        _exercises.update { list ->
            val ex = list.getOrNull(exerciseIndex) ?: return@update list
            if (setIndex !in ex.exercises.indices) return@update list

            val updatedExercise = ex.copy(
                exercises = ex.exercises.toMutableList().apply { removeAt(setIndex) }
            )

            list.toMutableList().apply { this[exerciseIndex] = updatedExercise }
        }
    }

    fun clearForm() {
        _workoutTitle.value = ""
        _showAddDialog.value = false
        _exercises.value = emptyList()
    }
}