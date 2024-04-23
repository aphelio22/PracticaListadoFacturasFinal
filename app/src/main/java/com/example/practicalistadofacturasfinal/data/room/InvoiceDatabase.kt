package com.example.practicalistadofacturasfinal.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicalistadofacturasfinal.MyApplication

/**
 * Clase abstracta que crea la Base de Datos.
 */
@Database(entities = [InvoiceModelRoom::class, EnergyDataModelRoom::class], version = 1, exportSchema = false)
abstract class InvoiceDatabase: RoomDatabase() {

    /**
     * Método que obtiene un objeto DAO de la clase InvoiceDAO.
     *
     * @return Un objeto de clase InvoiceDAO.
     */
    abstract fun getInvoiceDao(): InvoiceDAO
    abstract fun getEnergyDataDao(): EnergyDataDAO
    companion object{
        //Instancia de la Base de Datos.
        private var DB_INSTANCE: InvoiceDatabase? = null

        /**
         * Método que crea una instancia de la Base de Datos de facturas utilizando
         * la variable 'DB_INSTANCE'.
         *
         * @param context Contexto de la aplicación.
         * @return Una instancia de la Base de Datos.
         */
        fun getAppDBInstance(): InvoiceDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(MyApplication.context, InvoiceDatabase::class.java, "invoice_database")
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}