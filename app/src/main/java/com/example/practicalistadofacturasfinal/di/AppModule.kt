package com.example.practicalistadofacturasfinal.di

import android.content.Context
import androidx.room.Room
import co.infinum.retromock.Retromock
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.core.network.retromock.ResourceBodyFactory
import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceClient
import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceClientRetroMock
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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
                minimumFetchIntervalInSeconds = 1 // O el valor que necesites
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
            .baseUrl("https://viewnextandroid.wiremockapi.cloud/") //Introduce el enlace de la API aquí.
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