package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.domain.LoginUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginActivityViewModel: ViewModel() {

    private val loginUseCase = LoginUseCase(FirebaseAuth.getInstance())

    private val _loginResult = MutableLiveData<Result<FirebaseUser?>>()
    val loginResult: LiveData<Result<FirebaseUser?>>
        get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.invoke(email, password)
            _loginResult.postValue(result)
        }
    }

    // Método para verificar si la información de inicio de sesión es válida
    fun isLoginInfoValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}