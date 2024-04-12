package com.example.practicalistadofacturasfinal.data

import androidx.lifecycle.LiveData
import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceService
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.InvoiceDAO
import com.example.practicalistadofacturasfinal.data.room.InvoiceDatabase
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.domain.FetchInvoicesUseCase

class InvoiceRepository() {
    val invoiceDAO = InvoiceDatabase.getAppDBInstance().getAppDao()
    val api = InvoiceService()

    suspend fun getDataFromAPI(): List<InvoiceResponse>? {
        return api.getDataFromAPI()
    }

    suspend fun insertInvoices(invoices: List<InvoiceModelRoom>) {
        invoiceDAO.insertInvoices(invoices)
    }

    fun getAllInvoices(): List<InvoiceModelRoom> {
        return invoiceDAO.getAllInvoices()
    }

    suspend fun fetchAndInsertInvoicesFromAPI() {
        val invoicesFromAPI = api.getDataFromAPI() ?: emptyList()
        val invoicesRoom = invoicesFromAPI.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        insertInvoices(invoicesRoom)
    }
}