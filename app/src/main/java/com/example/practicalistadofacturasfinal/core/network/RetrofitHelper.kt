package com.example.practicalistadofacturasfinal.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid.wiremockapi.cloud/") //Introduce el enlace de la API aquí.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}