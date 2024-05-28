package com.example.practicalistadofacturasfinal.data

import android.util.Log
import co.infinum.retromock.Retromock
import com.example.practicalistadofacturasfinal.data.retrofit.network.AppService
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.Detail
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.EnergyDataDAO
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import com.example.practicalistadofacturasfinal.data.room.InvoiceDAO
import com.example.practicalistadofacturasfinal.data.room.InvoiceDatabase
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val invoiceDAO: InvoiceDAO, private val energyDao: EnergyDataDAO, private val firebaseRemoteConfig: FirebaseRemoteConfig, private val appService: AppService) {

    private suspend fun getEnergyDataFromRetromMock(): Detail? {
        return appService.getEnergyDataFromRetroMock()
    }

    private suspend fun getInvoicesFromAPI(): List<InvoiceResponse>? {
        return appService.getInvoicesFromAPI()
    }

    private suspend fun getInvoicesFromRetroMock(): List<InvoiceResponse>? {
        return appService.getInvoicesFromRetroMock()
    }

    private suspend fun getInvoicesFromKtor(): List<InvoiceResponse>? {
        return appService.getInvoicesFromKtor()
    }

    private fun insertEnergyDataInRoom(energyDataModelRoom: EnergyDataModelRoom) {
        energyDao.insertEnergyDataInRoom(energyDataModelRoom)
    }

    private fun insertInvoicesInRoom(invoices: List<InvoiceModelRoom>) {
        invoiceDAO.insertInvoicesInRoom(invoices)
    }

    fun getAllInvoicesFromRoom(): List<InvoiceModelRoom> {
        return invoiceDAO.getAllInvoicesFromRoom()
    }

    fun getEnergyDataFromRoom(): EnergyDataModelRoom {
        return energyDao.getEnergyDataFromRoom()
    }

    fun deleteAllInvoicesFromRoom() {
        invoiceDAO.deleteAllInvoicesFromRoom()
    }

    fun fetchAndActivateConfig() {
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //remoteConfig.getBoolean("showSwitch")
                    Log.d("ÉXITO", "Configuración remota activada")
                } else {
                    // Error al activar la configuración remota
                    val exception = task.exception
                    Log.d("ERROR", "Error al activar la configuración remota", exception)
                }
            }
    }

    fun getBooleanValue(key: String): Boolean {
        Log.d("INFO", firebaseRemoteConfig.getBoolean(key).toString())
        return firebaseRemoteConfig.getBoolean(key)
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

    suspend fun fetchAndInsertInvoicesFromKtor() {
        val invoicesFromKtor = getInvoicesFromKtor() ?: emptyList()
        val invoicesRoom = invoicesFromKtor.map { invoice ->
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