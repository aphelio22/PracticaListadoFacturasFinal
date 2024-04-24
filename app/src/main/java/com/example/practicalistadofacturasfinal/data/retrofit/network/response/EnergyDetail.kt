package com.example.practicalistadofacturasfinal.data.retrofit.network.response

import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom

data class EnergyDetail (
    val detail: Detail
) {
    fun asEnergyDataModelRoom(): EnergyDataModelRoom {
        return EnergyDataModelRoom(detail.cau, detail.requestStatus, detail.selfConsumptionType, detail.surplusCompensation, detail.installationPower)
    }
}



