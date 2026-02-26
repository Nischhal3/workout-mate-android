package com.example.workoutmate.utils

import android.content.Context
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toPrettyDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}

fun showToast(
    context: Context,
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(context, message, duration).show()
}