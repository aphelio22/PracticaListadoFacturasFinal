package com.example.practicalistadofacturasfinal.data.retrofit.network.response

import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom

data class EnergyDetail (
    val cau: String,
    val requestStatus: String,
    val selfConsumptionType: String,
    val surplusCompensation: String,
    val IntsllationPower: String
) {
    fun asEnergyDataModelRoom(): EnergyDataModelRoom {
        return EnergyDataModelRoom(cau, requestStatus, selfConsumptionType, surplusCompensation, IntsllationPower)
    }
}