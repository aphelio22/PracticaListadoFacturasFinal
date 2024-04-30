package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.domain.LogOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectionActivityMViewModel: ViewModel() {
    private val logOutUseCase = LogOutUseCase()

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUseCase.invoke()
        }
    }
}