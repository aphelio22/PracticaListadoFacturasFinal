package com.example.practicalistadofacturasfinal.data

import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceService
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse

class InvoiceRepository {
    val api = InvoiceService()

    suspend fun getDataFromAPI(): List<InvoiceResponse>? {
        return api.getDataFromAPI()
    }
}