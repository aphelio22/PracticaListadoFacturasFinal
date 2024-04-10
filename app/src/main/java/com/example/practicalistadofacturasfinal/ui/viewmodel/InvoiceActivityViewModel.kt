package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.domain.InvoiceUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class InvoiceActivityViewModel: ViewModel() {
    val invoiceUseCase = InvoiceUseCase()
    fun getInvoices(): LiveData<List<InvoiceResponse>> {
        val invoiceLiveData = MutableLiveData<List<InvoiceResponse>>()
        viewModelScope.launch {
            try {
                val invoices = invoiceUseCase() ?: emptyList()
                invoiceLiveData.postValue(invoices)
            } catch (e: Exception) {
                Log.d("Error", "Patata")
            }
        }
        return invoiceLiveData
    }
}