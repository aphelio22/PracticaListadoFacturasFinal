package com.example.practicalistadofacturasfinal.domain

import com.example.practicalistadofacturasfinal.data.InvoiceRepository
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse

class FetchInvoicesUseCase(private val repository: InvoiceRepository) {

    suspend operator fun invoke(): List<InvoiceResponse>? {
        return repository.getDataFromAPI()
    }
}