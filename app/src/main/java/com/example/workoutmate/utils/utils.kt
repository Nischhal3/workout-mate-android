package com.example.workoutmate.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toPrettyDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}