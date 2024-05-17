package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.LogOutUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectionActivityMViewModel: ViewModel() {
    private val logOutUseCase = LogOutUseCase(FirebaseAuth.getInstance())

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUseCase.invoke()
        }
    }
}