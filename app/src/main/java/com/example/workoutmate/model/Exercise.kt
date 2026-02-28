package com.example.workoutmate.model

data class SetEntry(
    val id: String, val weight: String, val reps: String
)

data class Exercise(
    val id: String, val name: String, val setList: List<SetEntry> = emptyList()
)