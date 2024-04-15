package com.example.practicalistadofacturasfinal.ui.model.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.databinding.ItemFacturasBinding
import com.example.practicalistadofacturasfinal.databinding.ItemPracticeSelectionBinding
import com.example.practicalistadofacturasfinal.ui.model.PracticeVO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class InvoiceViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemFacturasBinding.bind(view)

    fun render (item: InvoiceModelRoom, onClickListener: (InvoiceModelRoom) -> Unit) {
        binding.itemEstado.text = item.descEstado
        binding.itemImporte.text = item.importeOrdenacion.toString()
        binding.itemTvSelection.text = item.fecha?.let { formatDate(it) }.toString()

        itemView.setOnClickListener {
            onClickListener(item)
        }

        when (binding.itemEstado.text) {
            "Pendiente de pago" -> {
                binding.itemEstado.visibility = View.VISIBLE
                binding.itemEstado.setTextColor(Color.RED)
            }
            "Pagada" -> {
                binding.itemEstado.text = ""
            }
            else -> {
                binding.itemEstado.setTextColor(Color.GRAY)
            }
        }
    }
    fun formatDate(date: String): String {
        return try {
            val insert = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val format = insert.parse(date)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("es", "ES"))

            format?.let { outputFormat.format(it) } ?: date


        } catch (e: ParseException) {
            e.printStackTrace()
            date
        }
    }
}