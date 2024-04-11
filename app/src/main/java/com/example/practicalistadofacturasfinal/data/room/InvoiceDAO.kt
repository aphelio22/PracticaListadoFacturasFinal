package com.example.practicalistadofacturasfinal.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InvoiceDAO {
    @Query("SELECT * FROM invoice_table")
    fun getAllInvoices(): List<InvoiceModelRoom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoices(invoiceModelRoom: List<InvoiceModelRoom>)

    @Query("DELETE FROM invoice_table")
    fun deleteAllInvoices()
}