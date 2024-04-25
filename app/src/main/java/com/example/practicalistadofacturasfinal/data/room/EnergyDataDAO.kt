package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EnergyDataDAO {
    @Query("SELECT * FROM energyData_table")
    fun getEnergyDataFromRoom(): EnergyDataModelRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEnergyDataInRoom(energyDataModelRoom: EnergyDataModelRoom)

    @Query("DELETE FROM energyData_table")
    fun deleteEnergyDataFromRoom()
}