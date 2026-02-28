package com.example.workoutmate.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

fun LocalDate.toPrettyDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}

fun generateId(): String = UUID.randomUUID().toString()