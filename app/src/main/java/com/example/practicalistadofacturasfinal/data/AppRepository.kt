package com.example.practicalistadofacturasfinal.data

import com.example.practicalistadofacturasfinal.data.retrofit.network.InvoiceService
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.EnergyDetail
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import com.example.practicalistadofacturasfinal.data.room.InvoiceDatabase
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom

class AppRepository() {
    val invoiceDAO = InvoiceDatabase.getAppDBInstance().getInvoiceDao()
    val energyDao = InvoiceDatabase.getAppDBInstance().getEnergyDataDao()
    val api = InvoiceService()

    suspend fun getDataFromAPI(): List<InvoiceResponse>? {
        return api.getDataFromAPI()
    }

    suspend fun getDataFromMock(): List<InvoiceResponse>? {
        return api.getDataFromMock()
    }

    suspend fun getEnergyDataFromMock(): EnergyDetail? {
        return api.getDataEnergyFromMock()
    }

    suspend fun insertEnergyData(energyDataModelRoom: EnergyDataModelRoom) {
        energyDao.insertInvoices(energyDataModelRoom)
    }

    suspend fun insertInvoices(invoices: List<InvoiceModelRoom>) {
        invoiceDAO.insertInvoices(invoices)
    }

    fun getAllInvoices(): List<InvoiceModelRoom> {
        return invoiceDAO.getAllInvoices()
    }

    fun getAllEnergyData(): EnergyDataModelRoom {
        return energyDao.getAllEnergyData()
    }

    suspend fun fetchAndInsertInvoicesFromMock() {
        val invoicesFromMock = api.getDataFromMock() ?: emptyList()
        val invoicesRoom = invoicesFromMock.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        insertInvoices(invoicesRoom)
    }

    suspend fun fetchAndInsertInvoicesFromAPI() {
        val invoicesFromAPI = api.getDataFromAPI() ?: emptyList()
        val invoicesRoom = invoicesFromAPI.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        insertInvoices(invoicesRoom)
    }

    suspend fun fetchAndInsertEnergyDataFromMock() {
        val energyDetail = api.getDataEnergyFromMock()
        val energyDetailRoom = energyDetail?.asEnergyDataModelRoom()
        if (energyDetailRoom != null) {
            insertEnergyData(energyDetailRoom)
        }
    }
}