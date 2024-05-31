package com.example.practicalistadofacturasfinal.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import co.infinum.retromock.Retromock
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.core.network.retromock.ResourceBodyFactory
import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceClient
import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceClientRetroMock
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceRepositoryListResponse
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.EnergyDataDAO
import com.example.practicalistadofacturasfinal.data.room.InvoiceDAO
import com.example.practicalistadofacturasfinal.data.room.InvoiceDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideInvoiceDAO(invoiceDatabase: InvoiceDatabase): InvoiceDAO {
        return invoiceDatabase.getInvoiceDao()
    }

    @Provides
    fun provideEnergyDAO(invoiceDatabase: InvoiceDatabase): EnergyDataDAO {
        return invoiceDatabase.getEnergyDataDao()
    }

    @Provides
    @Singleton
    fun provideInvoiceDatabase(@ApplicationContext context: Context): InvoiceDatabase {
        return Room.databaseBuilder(context, InvoiceDatabase::class.java, "invoice_database").build()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance().apply {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 1
            }
            setConfigSettingsAsync(configSettings)
        }
    }

    @Singleton
    @Provides
    fun provideRemoteConfigManager(remoteConfig: FirebaseRemoteConfig): RemoteConfigManager {
        return RemoteConfigManager(remoteConfig)
    }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideInvoiceClient(retrofit: Retrofit): InvoiceClient {
        return retrofit.create(InvoiceClient::class.java)
    }

    @Provides
    @Singleton
    fun provideRetromock(retrofit: Retrofit): Retromock {
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(ResourceBodyFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

    @Provides
    @Singleton
    suspend fun getDataFromKtor(httpClient: HttpClient): List<InvoiceResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val invoices = httpClient.get("https://viewnextandroid.wiremockapi.cloud/facturas")
                    .body<InvoiceRepositoryListResponse>()
                    .facturas
                invoices
            } catch (e: Exception) {
                Log.e("Error", "Failed to fetch data from Ktor: ${e.message}")
                null
            }
        }
    }

    @Provides
    @Singleton
    fun provideInvoiceClientRetromock(retromock: Retromock): InvoiceClientRetroMock {
        return retromock.create(InvoiceClientRetroMock::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }
}