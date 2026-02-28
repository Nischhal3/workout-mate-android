package com.example.workoutmate.utils

import com.example.workoutmate.model.SetInputValidationResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

fun LocalDate.toPrettyDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}

fun generateId(): String = UUID.randomUUID().toString()

fun validateWorkoutFormInput(
    reps: String, weight: String
): SetInputValidationResult {
    val isRepsError = reps.isNotBlank() && reps.toIntOrNull() == null
    val isWeightError = weight.isNotBlank() && weight.toDoubleOrNull() == null
    val hasError = isRepsError || isWeightError

    val errorMessage = buildString {
        if (isWeightError) append("Weight must be a number. ")
        if (isRepsError) append("Reps must be a whole number.")
    }.trim()

    return SetInputValidationResult(
        hasError = hasError, errorMessage = errorMessage
    )
}