package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [InvoiceModelRoom::class, EnergyDataModelRoom::class], version = 1, exportSchema = false)
abstract class InvoiceDatabase: RoomDatabase() {

    abstract fun getInvoiceDao(): InvoiceDAO
    abstract fun getEnergyDataDao(): EnergyDataDAO
}