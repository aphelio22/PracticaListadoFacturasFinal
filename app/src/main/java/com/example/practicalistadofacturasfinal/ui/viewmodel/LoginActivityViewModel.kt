package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.LoginUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

    private val _loginResult = MutableLiveData<Result<FirebaseUser?>>()
    val loginResult: LiveData<Result<FirebaseUser?>>
        get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.invoke(email, password)
            _loginResult.postValue(result)
        }
    }

    fun isLoginInfoValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}