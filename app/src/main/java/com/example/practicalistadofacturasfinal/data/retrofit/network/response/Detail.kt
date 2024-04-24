package com.example.practicalistadofacturasfinal.data.retrofit.network.response

import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom

data class Detail (
    val cau: String,
    val requestStatus: String,
    val selfConsumptionType: String,
    val surplusCompensation: String,
    val installationPower: String
) {
    fun asEnergyDataModelRoom(): EnergyDataModelRoom {
        return EnergyDataModelRoom(
            cau,
            requestStatus,
            selfConsumptionType,
            surplusCompensation,
            installationPower
        )
    }
}