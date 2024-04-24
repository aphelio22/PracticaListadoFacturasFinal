package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Entity
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.EnergyDetail

@Entity(tableName = "energyData_table", primaryKeys = ["cau"])
class EnergyDataModelRoom (
    val cau: String,
    val requestStatus: String,
    val selfConsumptionType: String,
    val surplusCompensation: String,
    val installationPower: String
)