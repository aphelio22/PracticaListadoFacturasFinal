package com.example.practicalistadofacturasfinal.data.retrofit.network.response

data class InvoiceRepositoryListResponse(
    val numFacturas: Int,
    val facturas: List<InvoiceResponse>
)