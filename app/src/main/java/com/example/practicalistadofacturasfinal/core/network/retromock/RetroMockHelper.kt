package com.example.practicalistadofacturasfinal.core.network.retromock

import co.infinum.retromock.Retromock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroMockHelper {
    fun getRetromock(retrofit: Retrofit): Retromock {
        return Retromock.Builder()
            .retrofit(retrofit) //Introduce el enlace de la API aquí.
            .defaultBodyFactory(ResourceBodyFactory())
            .build()
    }
}