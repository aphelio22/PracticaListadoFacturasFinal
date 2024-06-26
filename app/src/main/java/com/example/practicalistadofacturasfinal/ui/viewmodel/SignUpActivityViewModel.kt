package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.SignUpUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpActivityViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase): ViewModel() {

    private val _signUpResult = MutableLiveData<Result<FirebaseUser?>>()
    val signUpResult: LiveData<Result<FirebaseUser?>>
        get() = _signUpResult

    fun signUp(email: String, password: String, confirmPassword: String) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            val errorMessage = when {
                email.isEmpty() -> "No hay email"
                password.isEmpty() -> "Faltan las contraseñas"
                confirmPassword.isEmpty() -> "Falta la contraseña de confirmación"
                else -> "Fatan el email y las contraseñas"
            }
            _signUpResult.postValue(Result.failure(IllegalArgumentException(errorMessage)))
        } else if (password != confirmPassword) {
            _signUpResult.postValue(Result.failure(IllegalArgumentException("Las contraseñas no coinciden")))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val result = signUpUseCase.invoke(email, password, confirmPassword)
                _signUpResult.postValue(result)
            }
        }
    }
}