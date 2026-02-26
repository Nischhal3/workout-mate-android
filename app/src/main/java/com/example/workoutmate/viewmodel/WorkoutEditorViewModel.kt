package com.example.workoutmate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workoutmate.model.Exercise
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

    fun deleteExerciseSet(exerciseIndex: Int) {
        _exercises.update { list ->
            if (exerciseIndex !in list.indices) return@update list
            list.toMutableList().also { it.removeAt(exerciseIndex) }
        }
    }

    fun updateEntry(
        exerciseIndex: Int, entryIndex: Int, newWeight: String, newReps: String
    ) {
        _exercises.update { list ->
            if (exerciseIndex !in list.indices) return@update list
            val ex = list[exerciseIndex]
            if (entryIndex !in ex.exercises.indices) return@update list

            val updatedExercise = ex.copy(
                exercises = ex.exercises.mapIndexed { j, entry ->
                    if (j != entryIndex) entry else entry.copy(weight = newWeight, reps = newReps)
                })

            list.mapIndexed { i, item -> if (i != exerciseIndex) item else updatedExercise }
        }
    }

    fun deleteEntry(exerciseIndex: Int, entryIndex: Int) {
        _exercises.update { list ->
            if (exerciseIndex !in list.indices) return@update list
            val ex = list[exerciseIndex]
            if (entryIndex !in ex.exercises.indices) return@update list

            val updatedExercise = ex.copy(
                exercises = ex.exercises.toMutableList().also { it.removeAt(entryIndex) })

            list.mapIndexed { i, item -> if (i != exerciseIndex) item else updatedExercise }
        }
    }

    fun clear() {
        _workoutTitle.value = ""
        _showAddDialog.value = false
        _exercises.value = emptyList()
    }
}