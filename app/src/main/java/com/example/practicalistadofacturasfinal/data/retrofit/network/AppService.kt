package com.example.practicalistadofacturasfinal.data.retrofit.network

import android.util.Log
import co.infinum.retromock.Retromock
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.Detail
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import retrofit2.Retrofit
import javax.inject.Inject

class AppService @Inject constructor(private val retromockBuilder: Retromock, private val retrofitBuilder: Retrofit) {

    suspend fun getInvoicesFromRetroMock(): List<InvoiceResponse>? {
        val response = retromockBuilder.create(InvoiceClientRetroMock::class.java).getDataFromAPI()
        return if (response.isSuccessful) {
            val invoices = response.body()?.facturas
            if (invoices.isNullOrEmpty()) {
                emptyList()
            } else {
                invoices
            }
        } else {
            Log.d("Failure", response.toString())
            null
        }
    }

    suspend fun getInvoicesFromAPI(): List<InvoiceResponse>? {
        val response = retrofitBuilder.create(InvoiceClient::class.java).getDataFromAPI()
        return if (response.isSuccessful) {
            val invoices = response.body()?.facturas
            if (invoices.isNullOrEmpty()) {
                emptyList()
            } else {
                invoices
            }
        } else {
            Log.d("Failure", response.toString())
            null
        }
    }

    suspend fun getEnergyDataFromRetroMock(): Detail? {
        val response = retromockBuilder.create(EnergyDataRetroMock::class.java).getDataEnergyFromMock()
        return if (response.isSuccessful && response.body() != null) {
            val energyData = response.body()
            energyData
        } else{
            Log.d("ENERGY", "Algo salio mal")
            null
        }
    }
}