package com.example.workoutmate.model

data class SetEntry(
    val weight: String, val reps: String
)

data class Exercise(
    val name: String, val exercises: List<SetEntry> = emptyList()
)