package com.example.practicalistadofacturasfinal.data.retrofit.network

import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceRepositoryListResponse
import retrofit2.Response
import retrofit2.http.GET

interface InvoiceClient {
    @GET("facturas") //Final de la URL
    suspend fun getDataFromAPI(): Response<InvoiceRepositoryListResponse>
}
