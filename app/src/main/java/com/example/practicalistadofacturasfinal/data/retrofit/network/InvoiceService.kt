package com.example.practicalistadofacturasfinal.data.retrofit.network

import android.util.Log
import com.example.practicalistadofacturasfinal.core.network.retrofit.RetrofitHelper
import com.example.practicalistadofacturasfinal.core.network.retromock.RetroMockHelper
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.Detail
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse

class InvoiceService {
    private val retrofitBuilder = RetrofitHelper.getRetrofit()
    private val retromockBuilder = RetroMockHelper.getRetromock(retrofitBuilder)

    suspend fun getInvoicesFromRetroMock(): List<InvoiceResponse>? {
        val response = retromockBuilder.create(InvoiceClientRetroMock::class.java).getDataFromAPI()
        if (response.isSuccessful) {
            val invoices = response.body()?.facturas
            if (invoices.isNullOrEmpty()) {
                return emptyList()
            } else {
                return invoices
            }
        } else {
            Log.d("Failure", response.toString())
            return null
        }
    }

    suspend fun getInvoicesFromAPI(): List<InvoiceResponse>? {
            val response = retrofitBuilder.create(InvoiceClient::class.java).getDataFromAPI()
           if (response.isSuccessful) {
               val invoices = response.body()?.facturas
               if (invoices.isNullOrEmpty()) {
                   return emptyList()
               } else {
                   return invoices
               }
           } else {
               Log.d("Failure", response.toString())
               return null
           }
    }

    suspend fun getEnergyDataFromRetroMock(): Detail? {
        val response = retromockBuilder.create(EnergyDataRetroMock::class.java).getDataEnergyFromMock()
        if (response.isSuccessful && response.body() != null) {
            val energyData = response.body()
            return energyData
        } else{
            Log.d("ENERGY", "Algo salio mal")
            return null
        }
    }
}