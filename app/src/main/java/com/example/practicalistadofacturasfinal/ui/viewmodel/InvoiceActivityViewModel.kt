package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.data.InvoiceRepository
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.domain.FetchInvoicesUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class InvoiceActivityViewModel() : ViewModel() {
    private lateinit var invoiceRepository: InvoiceRepository
    private lateinit var fetchInvoicesUseCase: FetchInvoicesUseCase

    private val _invoiceLiveData = MutableLiveData<List<InvoiceModelRoom>>()
    val invoiceLiveData: LiveData<List<InvoiceModelRoom>>
        get() = _invoiceLiveData

    fun fetchInvoices() {
        viewModelScope.launch {
            _invoiceLiveData.postValue(invoiceRepository.getAllInvoices())
            try {
                if (isInternetAvailable()) {
                    invoiceRepository.fetchAndInsertInvoicesFromAPI()
                    _invoiceLiveData.postValue(invoiceRepository.getAllInvoices())
                }
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
            }
        }
    }

    fun initRepository() {
        invoiceRepository = InvoiceRepository()
    }

    fun initFetchUseCase() {
        fetchInvoicesUseCase = FetchInvoicesUseCase(invoiceRepository)
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }
}