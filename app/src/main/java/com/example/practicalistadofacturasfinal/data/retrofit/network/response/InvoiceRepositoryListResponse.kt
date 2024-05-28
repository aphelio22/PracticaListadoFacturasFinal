package com.example.practicalistadofacturasfinal.data.retrofit.network.response

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceRepositoryListResponse(
    val numFacturas: Int,
    val facturas: List<InvoiceResponse>
)