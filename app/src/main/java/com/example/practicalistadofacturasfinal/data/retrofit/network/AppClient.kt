package com.example.practicalistadofacturasfinal.data.retrofit.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockBehavior
import co.infinum.retromock.meta.MockCircular
import co.infinum.retromock.meta.MockResponse
import co.infinum.retromock.meta.MockResponses
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.Detail
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceRepositoryListResponse
import retrofit2.Response
import retrofit2.http.GET

interface InvoiceClientRetroMock {
    @Mock
    @MockResponses(
        MockResponse(body = "mock3.json"),
        MockResponse(body = "mock2.json"),
        MockResponse(body = "mock.json")
    )
    @MockCircular
    //@MockBehavior(durationDeviation = 1000, durationMillis = 20000)
    @GET("resources")
    suspend fun getDataFromAPI(): Response<InvoiceRepositoryListResponse>
}

interface EnergyDataRetroMock {
    @Mock
    @MockResponses(
        MockResponse(body = "mock4.json")
    )
    @MockCircular
    @GET("resources")
    suspend fun getDataEnergyFromMock(): Response<Detail>
}

interface InvoiceClient {
    @GET("facturas")
    suspend fun getDataFromAPI(): Response<InvoiceRepositoryListResponse>
}
