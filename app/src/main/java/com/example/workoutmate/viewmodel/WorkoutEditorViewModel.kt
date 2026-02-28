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

    private val _addExerciseDialogIsVisible = MutableStateFlow(false)
    val addExerciseDialogIsVisible: StateFlow<Boolean> = _addExerciseDialogIsVisible.asStateFlow()

    fun setWorkoutTitle(title: String) {
        _workoutTitle.value = title
    }

    fun openAddExerciseDialog() {
        _addExerciseDialogIsVisible.value = true
    }

    fun closeAddExerciseDialog() {
        _addExerciseDialogIsVisible.value = false
    }

    fun addExercise(exercise: Exercise) {
        _exercises.update { it + exercise }
        _addExerciseDialogIsVisible.value = false
    }

    fun updateExerciseName(id: String, newName: String) {
        _exercises.update { list ->
            list.map { exercise ->
                if (exercise.id == id) exercise.copy(name = newName)
                else exercise
            }
        }
    }

    fun updateSet(
        exerciseId: String, newSetEntry: SetEntry
    ) {
        if (newSetEntry.weight.toDoubleOrNull() == null || newSetEntry.reps.toIntOrNull() == null) return

        _exercises.update { list ->
            list.map { exercise ->
                if (exercise.id == exerciseId) {
                    exercise.copy(
                        setList = exercise.setList.map { set ->
                            if (set.id == newSetEntry.id) newSetEntry
                            else set
                        })
                } else exercise
            }
        }
    }

    fun deleteExercise(exerciseId: String) {
        _exercises.update { list ->
            list.filter { it.id != exerciseId }
        }
    }

    fun deleteSet(
        exerciseId: String, setId: String
    ) {
        _exercises.update { list ->
            list.map { exercise ->
                if (exercise.id == exerciseId) {
                    exercise.copy(
                        setList = exercise.setList.filter { it.id != setId })
                } else exercise
            }
        }
    }

    fun clearForm() {
        _workoutTitle.value = ""
        _addExerciseDialogIsVisible.value = false
        _exercises.value = emptyList()
    }
}