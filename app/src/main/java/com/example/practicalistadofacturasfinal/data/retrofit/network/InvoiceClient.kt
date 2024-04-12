package com.example.practicalistadofacturasfinal.data.retrofit.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockCircular
import co.infinum.retromock.meta.MockResponse
import co.infinum.retromock.meta.MockResponses
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceRepositoryListResponse
import retrofit2.Response
import retrofit2.http.GET

interface InvoiceClientRetroMock {
    @Mock
    @MockResponses(
        MockResponse(body = "mock.json"),
        MockResponse(body = "mock2.json")
    )
    @MockCircular
    @GET("resources")
    suspend fun getDataFromAPI(): Response<InvoiceRepositoryListResponse>
}

interface InvoiceClient {
    @GET("facturas") //Final de la URL
    suspend fun getDataFromAPI(): Response<InvoiceRepositoryListResponse>
}
