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

    private var useAPI = true

    fun fetchInvoices() {
        viewModelScope.launch {
            _invoiceLiveData.postValue(invoiceRepository.getAllInvoices())
            try {
                if (isInternetAvailable()) {
                    when(useAPI){
                        true -> invoiceRepository.fetchAndInsertInvoicesFromAPI()
                        false -> invoiceRepository.fetchAndInsertInvoicesFromMock()
                    }
                    _invoiceLiveData.postValue(invoiceRepository.getAllInvoices())
                }
            } catch (e: Exception) {
                Log.d("Error", e.printStackTrace().toString())
            }
        }
    }

    fun initRepository() {
        invoiceRepository = InvoiceRepository()
    }

    fun initFetchUseCase() {
        fetchInvoicesUseCase = FetchInvoicesUseCase(invoiceRepository)
    }

    fun switchMode(apiMode: Boolean) {
        invoiceRepository.invoiceDAO.deleteAllInvoices()
        useAPI = apiMode
        fetchInvoices()
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