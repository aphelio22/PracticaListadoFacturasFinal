package com.example.practicalistadofacturasfinal.data.retrofit.network

import android.util.Log
import com.example.practicalistadofacturasfinal.core.network.retrofit.RetrofitHelper
import com.example.practicalistadofacturasfinal.core.network.retromock.RetroMockHelper
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse

class InvoiceService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val retromock = RetroMockHelper.getRetromock(retrofit)

    suspend fun getDataFromMock(): List<InvoiceResponse>? {
        val response = retromock.create(InvoiceClientRetroMock::class.java).getDataFromAPI()
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
            val response = retrofit.create(InvoiceClient::class.java).getDataFromAPI()
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