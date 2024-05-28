package com.example.practicalistadofacturasfinal.data.retrofit.network.response

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceResponse(
    val descEstado: String,
    val fecha: String,
    val importeOrdenacion: Double
)
