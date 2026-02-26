package com.example.workoutmate.model

sealed interface UiEvent {
    data class ShowError(val message: String) : UiEvent
    data class ShowSuccess(val message: String) : UiEvent
}