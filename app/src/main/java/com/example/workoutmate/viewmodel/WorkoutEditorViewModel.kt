package com.example.workoutmate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workoutmate.model.Exercise
import com.example.workoutmate.model.SetEntry
import com.example.workoutmate.model.UiEvent
import com.example.workoutmate.utils.UiEvents
import com.example.workoutmate.utils.generateId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "WorkoutEditorViewModel"

class WorkoutEditorViewModel : ViewModel() {

    private val _workoutTitle = MutableStateFlow("")
    val workoutTitle: StateFlow<String> = _workoutTitle.asStateFlow()

    private val _draftExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val draftExercises: StateFlow<List<Exercise>> = _draftExercises.asStateFlow()

    private val _exerciseName = MutableStateFlow("")
    val exerciseName: StateFlow<String> = _exerciseName.asStateFlow()

    private val _newSetList = MutableStateFlow(listOf(SetEntry()))
    val newSetList: StateFlow<List<SetEntry>> = _newSetList.asStateFlow()

    private val _addExerciseDialogIsVisible = MutableStateFlow(false)
    val addExerciseDialogIsVisible: StateFlow<Boolean> = _addExerciseDialogIsVisible.asStateFlow()

    fun setWorkoutTitle(title: String) {
        _workoutTitle.value = title
    }

    fun openAddExerciseDialog() {
        _addExerciseDialogIsVisible.value = true
    }

    fun closeAddExerciseDialog() {
        clearNewExercise()
        _addExerciseDialogIsVisible.value = false
    }

    // ---------------- New Exercise & Sets ----------------
    fun onExerciseNameChange(value: String) {
        _exerciseName.value = value
    }

    fun onSetFieldChange(setId: String, reps: String? = null, weight: String? = null) {
        if (reps == null && weight == null) return

        _newSetList.update { sets ->
            var changed = false
            val updated = sets.map { set ->
                if (set.id != setId) return@map set

                val newSet = set.copy(
                    weight = weight ?: set.weight, reps = reps ?: set.reps
                )
                if (newSet != set) changed = true
                newSet
            }
            if (changed) updated else sets
        }
    }

    fun addNewSet() {
        _newSetList.update { it + SetEntry(id = generateId(), weight = "", reps = "") }
    }

    fun addNewExercise() {
        _draftExercises.update {
            it + Exercise(
                id = generateId(), name = exerciseName.value, setList = newSetList.value
            )
        }
        _addExerciseDialogIsVisible.value = false
    }

    fun deleteNewSet(setId: String) {
        _newSetList.update { current ->
            val updated = current.filter { it.id != setId }
            updated.ifEmpty { listOf(SetEntry()) }
        }
    }

    fun clearNewExercise() {
        _exerciseName.value = ""
        _newSetList.value = listOf(SetEntry())
    }

    // ---------------- Draft Exercises & Sets ----------------
    fun addNewSetToDraftExerciseList(setEntry: SetEntry, exerciseId: String) {
        _draftExercises.update { exercises ->
            exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val updatedExercise = exercise.copy(
                        setList = exercise.setList + setEntry
                    )

                    UiEvents.tryEmit(
                        UiEvent.ShowSuccess(
                            message = "Added Set ${updatedExercise.setList.size} for exercise ${exercise.name}."
                        )
                    )

                    updatedExercise
                } else {
                    exercise
                }
            }
        }
    }

    fun updateDraftExerciseName(id: String, newName: String) {
        _draftExercises.update { list ->
            list.map { exercise ->
                if (exercise.id == id) exercise.copy(name = newName)
                else exercise
            }
        }
    }

    fun updateDraftSet(
        exerciseId: String, newSetEntry: SetEntry
    ) {
        if (newSetEntry.weight.toDoubleOrNull() == null || newSetEntry.reps.toIntOrNull() == null) return

        _draftExercises.update { list ->
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

    fun deleteDraftSet(
        setId: String, exerciseId: String
    ) {
        _draftExercises.update { list ->
            list.map { exercise ->
                if (exercise.id == exerciseId) {
                    exercise.copy(
                        setList = exercise.setList.filter { it.id != setId })
                } else exercise
            }
        }
    }

    fun deleteDraftExercise(exerciseId: String) {
        _draftExercises.update { list ->
            list.filter { it.id != exerciseId }
        }
    }

    fun clearForm() {
        clearNewExercise()
        _workoutTitle.value = ""
        _draftExercises.value = emptyList()
        _addExerciseDialogIsVisible.value = false
    }
}