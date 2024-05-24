package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.ForgotPassUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPassViewModel @Inject constructor(private val forgotPassUseCase: ForgotPassUseCase): ViewModel() {
    private val _resetPassResult = MutableLiveData<Result<Unit>>()
    val resetPassResult: LiveData<Result<Unit>>
        get() = _resetPassResult

    fun resetPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = forgotPassUseCase.invoke(email)
            _resetPassResult.postValue(result)
        }
    }

    fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty()
    }
}