package com.example.practicalistadofacturasfinal.data

import com.example.practicalistadofacturasfinal.data.retrofit.network.AppService
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.Detail
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import com.example.practicalistadofacturasfinal.data.room.InvoiceDatabase
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom

class AppRepository() {
    val invoiceDAO = InvoiceDatabase.getAppDBInstance().getInvoiceDao()
    val energyDao = InvoiceDatabase.getAppDBInstance().getEnergyDataDao()
    val api = AppService()

    suspend fun getEnergyDataFromRetromMock(): Detail? {
        return api.getEnergyDataFromRetroMock()
    }

    suspend fun getInvoicesFromAPI(): List<InvoiceResponse>? {
        return api.getInvoicesFromAPI()
    }

    suspend fun getInvoicesFromRetroMock(): List<InvoiceResponse>? {
        return api.getInvoicesFromRetroMock()
    }

    suspend fun insertEnergyDataInRoom(energyDataModelRoom: EnergyDataModelRoom) {
        energyDao.insertEnergyDataInRoom(energyDataModelRoom)
    }

    suspend fun insertInvoicesInRoom(invoices: List<InvoiceModelRoom>) {
        invoiceDAO.insertInvoicesInRoom(invoices)
    }

    fun getAllInvoicesFromRoom(): List<InvoiceModelRoom> {
        return invoiceDAO.getAllInvoicesFromRoom()
    }

    fun getEnergyDataFromRoom(): EnergyDataModelRoom {
        return energyDao.getEnergyDataFromRoom()
    }

    suspend fun fetchAndInsertInvoicesFromMock() {
        val invoicesFromMock = getInvoicesFromRetroMock() ?: emptyList()
        val invoicesRoom = invoicesFromMock.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        insertInvoicesInRoom(invoicesRoom)
    }

    suspend fun fetchAndInsertInvoicesFromAPI() {
        val invoicesFromAPI = getInvoicesFromAPI() ?: emptyList()
        val invoicesRoom = invoicesFromAPI.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        insertInvoicesInRoom(invoicesRoom)
    }

    suspend fun fetchAndInsertEnergyDataFromMock() {
        val energyDetail = getEnergyDataFromRetromMock()
        val energyDetailRoom = energyDetail?.asEnergyDataModelRoom()
        if (energyDetailRoom != null) {
            insertEnergyDataInRoom(energyDetailRoom)
        }
    }
}