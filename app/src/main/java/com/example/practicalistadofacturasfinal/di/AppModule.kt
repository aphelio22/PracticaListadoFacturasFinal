package com.example.practicalistadofacturasfinal.di

import com.example.practicalistadofacturasfinal.data.AppRepository
import org.koin.dsl.module



val appModule = module {
    single { AppRepository() }
}


