package com.example.practicalistadofacturasfinal

import android.app.Application
import android.content.Context

class MyApplication: Application() {

        override fun onCreate() {
            super.onCreate()
            context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}