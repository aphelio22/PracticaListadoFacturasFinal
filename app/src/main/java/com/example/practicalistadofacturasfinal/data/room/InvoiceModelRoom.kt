package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Entity

@Entity(tableName = "invoice_table", primaryKeys = ["importeOrdenacion", "fecha"])
class InvoiceModelRoom(
    val descEstado: String,
    val importeOrdenacion: Double,
    val fecha: String
)