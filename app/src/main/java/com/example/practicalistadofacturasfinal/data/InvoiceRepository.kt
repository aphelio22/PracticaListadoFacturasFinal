package com.example.practicalistadofacturasfinal.data

import androidx.lifecycle.LiveData
import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceService
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.InvoiceDAO
import com.example.practicalistadofacturasfinal.data.room.InvoiceDatabase
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom

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
}