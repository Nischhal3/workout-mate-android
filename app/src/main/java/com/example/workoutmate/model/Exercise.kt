package com.example.workoutmate.model

import com.example.workoutmate.utils.generateId

data class SetEntry(
    val reps: String = "",
    val weight: String = "",
    val id: String = generateId()
)
data class Exercise(
    val id: String, val name: String, val setList: List<SetEntry> = emptyList()
)

data class SetInputValidationResult(
    val hasError: Boolean,
    val errorMessage: String,
)