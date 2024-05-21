package com.example.practicalistadofacturasfinal

import android.app.Application
import android.content.Context
import com.example.practicalistadofacturasfinal.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {

        override fun onCreate() {
            super.onCreate()
            context = applicationContext

            startKoin {
                androidLogger()
                androidContext(this@MyApplication)
                modules(appModule)
            }
    }

    companion object {
        lateinit var context: Context
    }
}