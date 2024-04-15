package com.example.practicalistadofacturasfinal.data.retrofit.network

import android.util.Log
import com.example.practicalistadofacturasfinal.core.network.retrofit.RetrofitHelper
import com.example.practicalistadofacturasfinal.core.network.retromock.RetroMockHelper
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse

class InvoiceService {
    private val retrofitBuilder = RetrofitHelper.getRetrofit()
    private val retromockBuilder = RetroMockHelper.getRetromock(retrofitBuilder)

    suspend fun getDataFromMock(): List<InvoiceResponse>? {
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

    suspend fun getDataFromAPI(): List<InvoiceResponse>? {
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
}