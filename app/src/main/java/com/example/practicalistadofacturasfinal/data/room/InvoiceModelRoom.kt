package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoice_table", primaryKeys = ["importeOrdenacion", "fecha"])
class InvoiceModelRoom(
    val descEstado: String,
    val importeOrdenacion: Double,
    val fecha: String
)