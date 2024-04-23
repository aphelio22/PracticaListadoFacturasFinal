package com.example.practicalistadofacturasfinal.data.retrofit.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockCircular
import co.infinum.retromock.meta.MockResponse
import co.infinum.retromock.meta.MockResponses
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.EnergyDetail
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
    @GET("resources")
    suspend fun getDataFromAPI(): Response<InvoiceRepositoryListResponse>
}

interface EnergyDataRetroMock {
    @Mock
    @MockResponses(
        MockResponse(body = "mock4.json")
    )
    @MockCircular
    @GET("resoruces")
    suspend fun getDataEnergyFromMock(): Response<EnergyDetail>
}

interface InvoiceClient {
    @GET("/") //Final de la URL. FIXME desactivada por ahora.
    suspend fun getDataFromAPI(): Response<InvoiceRepositoryListResponse>
}
