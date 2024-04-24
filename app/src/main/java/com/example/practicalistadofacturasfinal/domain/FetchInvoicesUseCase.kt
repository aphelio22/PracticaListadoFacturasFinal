package com.example.practicalistadofacturasfinal.domain

import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse

class FetchInvoicesUseCase(private val repository: AppRepository) {

    suspend operator fun invoke(): List<InvoiceResponse>? {
        return repository.getDataFromAPI()
    }

    suspend fun invokeMock(): List<InvoiceResponse>? {
        return repository.getDataFromMock()
    }
}