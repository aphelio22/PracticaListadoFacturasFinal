package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InvoiceDAO {
    @Query("SELECT * FROM invoice_table")
    fun getAllInvoicesFromRoom(): List<InvoiceModelRoom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoicesInRoom(invoiceModelRoom: List<InvoiceModelRoom>)

    @Query("DELETE FROM invoice_table")
    fun deleteAllInvoicesFromRoom()
}