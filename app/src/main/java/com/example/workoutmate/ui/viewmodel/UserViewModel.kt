package com.example.workoutmate.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutmate.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository.getInstance(application)

    fun createUser(username: String) {
        viewModelScope.launch {
            repository.createUser(username)
        }
    }

    fun getUserById(id: Long, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserById(id)
            onResult(user?.username)
        }
    }
}