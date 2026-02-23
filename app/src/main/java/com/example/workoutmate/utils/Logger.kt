package com.example.workoutmate.utils

import android.util.Log

enum class LogType { DEBUG, INFO, WARNING, ERROR }

object Logger {

    operator fun invoke(
        tag: String, message: String, type: LogType = LogType.DEBUG, throwable: Throwable? = null
    ) {
        when (type) {
            LogType.DEBUG -> Log.d(tag, message, throwable)
            LogType.INFO -> Log.i(tag, message, throwable)
            LogType.WARNING -> Log.w(tag, message, throwable)
            LogType.ERROR -> Log.e(tag, message, throwable)
        }
    }
}