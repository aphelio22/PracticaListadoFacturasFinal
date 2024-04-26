package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivityViewModel: ViewModel() {

    private val loginUseCase = LoginUseCase()

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.login(email, password)
            result.fold(
                onSuccess = {
                    onSuccess.invoke()
                },
                onFailure = {
                    onError.invoke(it.localizedMessage ?: "Unknown error occurred")
                }
            )
        }
    }
}