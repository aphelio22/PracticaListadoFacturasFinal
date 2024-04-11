package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.data.InvoiceRepository
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.domain.FetchInvoicesUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class InvoiceActivityViewModel(): ViewModel() {
    private lateinit var invoiceRepository: InvoiceRepository
    private lateinit var fetchInvoicesUseCase: FetchInvoicesUseCase
    private val _invoiceLiveData = MutableLiveData<List<InvoiceModelRoom>>()
     val invoiceLiveData: LiveData<List<InvoiceModelRoom>>
        get() = _invoiceLiveData


    fun fetchInvoices() {
        viewModelScope.launch {
            try {
                val invoicesFromAPI = fetchInvoicesUseCase() ?: emptyList()

                // Convertir a InvoiceModelRoom y guardar en la base de datos Room
                val invoicesRoom = invoicesFromAPI.map { invoice ->
                    InvoiceModelRoom(
                        descEstado = invoice.descEstado,
                        importeOrdenacion = invoice.importeOrdenacion,
                        fecha = invoice.fecha
                    )
                }
                invoiceRepository.insertInvoices(invoicesRoom)
                _invoiceLiveData.postValue(invoiceRepository.getAllInvoices())
            } catch (e: Exception) {
                Log.d("Error", "Patata")
            }
        }
    }

    fun initFetchUseCase() {
        fetchInvoicesUseCase = FetchInvoicesUseCase(invoiceRepository)
    }
    fun initRepository(){
        invoiceRepository = InvoiceRepository()
    }
}