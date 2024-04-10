package com.example.practicalistadofacturasfinal.ui.model.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.databinding.ItemFacturasBinding
import com.example.practicalistadofacturasfinal.databinding.ItemPracticeSelectionBinding
import com.example.practicalistadofacturasfinal.ui.model.PracticeVO

class InvoiceViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemFacturasBinding.bind(view)

    fun render (item: InvoiceResponse, onClickListener: (InvoiceResponse) -> Unit) {
        binding.itemEstado.text = item.descEstado
        binding.itemImporte.text = item.importeOrdenacion.toString()
        binding.itemTvSelection.text = item.fecha

        itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}