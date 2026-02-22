package com.example.workoutmate.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutmate.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository.getInstance(application)

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    fun createUser(
        username: String, onError: (String) -> Unit, onSuccess: () -> Unit
    ) {
        val trimmed = username.trim()
        if (trimmed.isEmpty()) {
            onError("Username cannot be empty")
            return
        }

        viewModelScope.launch {
            val exists = userRepository.usernameExists(trimmed)
            if (exists) {
                onError("User already exists")
            } else {
                userRepository.createUser(trimmed)
                onSuccess()
            }
        }
    }

    fun loginByUsername(
        username: String, onError: (String) -> Unit, onSuccess: () -> Unit
    ) {
        val trimmed = username.trim()
        if (trimmed.isEmpty()) {
            onError("Enter username")
            return
        }

        viewModelScope.launch {
            val exists = userRepository.usernameExists(trimmed)
            if (exists) {
                _username.value = trimmed
                onSuccess()
            } else {
                onError("User not found. Create user first.")
            }
        }
    }
}