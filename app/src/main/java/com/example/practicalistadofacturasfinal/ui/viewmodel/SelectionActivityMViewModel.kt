package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.LogOutUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionActivityMViewModel @Inject constructor(private val logOutUseCase: LogOutUseCase): ViewModel() {

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUseCase.invoke()
        }
    }
}