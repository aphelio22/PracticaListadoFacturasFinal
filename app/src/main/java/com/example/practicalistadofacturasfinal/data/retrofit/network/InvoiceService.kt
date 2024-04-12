package com.example.practicalistadofacturasfinal.data.retrofit.network

import android.util.Log
import com.example.practicalistadofacturasfinal.core.network.RetrofitHelper
import com.example.practicalistadofacturasfinal.data.InvoiceRepository
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceRepositoryListResponse
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InvoiceService {
    private val retrofit = RetrofitHelper.getRetrofit()

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