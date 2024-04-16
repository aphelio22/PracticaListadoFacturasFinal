package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.data.InvoiceRepository
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.domain.FetchInvoicesUseCase
import com.example.practicalistadofacturasfinal.ui.model.FilterVO
import kotlinx.coroutines.launch
import java.lang.Exception

class InvoiceActivityViewModel() : ViewModel() {
    private lateinit var invoiceRepository: InvoiceRepository
    private lateinit var fetchInvoicesUseCase: FetchInvoicesUseCase

    private val _invoiceLiveData = MutableLiveData<List<InvoiceModelRoom>>()
    val invoiceLiveData: LiveData<List<InvoiceModelRoom>>
        get() = _invoiceLiveData

    private var _maxAmount: Float = 0.0f
    var maxAmount = 0.0f
        get() =_maxAmount

    private var _filterLiveData = MutableLiveData<FilterVO>()
    val filterLiveData: LiveData<FilterVO>
        get() = _filterLiveData

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

    fun findMaxAmount(invoiceList: List<InvoiceModelRoom>) {
        var max = 0.0

        for (invoice in invoiceList) {
            val actualInvoiceAmount = invoice.importeOrdenacion
            if (max < actualInvoiceAmount) {
                max = actualInvoiceAmount
            }
        }
        _maxAmount = max.toFloat()
    }

    fun applyFilters(maxDate: String, minDate: String, maxValueSlider: Double, status: HashMap<String, Boolean>) {
        if ((minDate == getString(MyApplication.context, R.string.dayMonthYear) && maxDate == getString(MyApplication.context, R.string.dayMonthYear)) ||
            (minDate != getString(MyApplication.context, R.string.dayMonthYear) && maxDate != getString(MyApplication.context, R.string.dayMonthYear))
        ) {
            val filter = FilterVO(maxDate, minDate, maxValueSlider, status)
            _filterLiveData.postValue(filter)
        } else {
            // Si alguna de las dos fechas, o las dos, no equivale a "Dia/Mes/Año", se realiza el intent, sino salta el PopUp.
        }
    }
}