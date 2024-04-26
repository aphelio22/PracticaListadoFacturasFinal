package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.SignUpUseCase
import com.example.practicalistadofacturasfinal.ui.activities.SignUpActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivityViewModel(): ViewModel() {

   private val signUpUseCase = SignUpUseCase()
    fun signUp(email: String, password: String, confirmPassword: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = signUpUseCase.signUp(email, password, confirmPassword)
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