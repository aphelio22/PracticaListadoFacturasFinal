package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EnergyDataDAO {
    @Query("SELECT * FROM energyData_table")
    fun getAllEnergyData(): EnergyDataModelRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoices(energyDataModelRoom: EnergyDataModelRoom)

    @Query("DELETE FROM energyData_table")
    fun deleteAllEnergyData()
}