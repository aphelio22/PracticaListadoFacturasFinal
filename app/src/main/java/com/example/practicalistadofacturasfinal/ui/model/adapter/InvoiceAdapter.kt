package com.example.practicalistadofacturasfinal.ui.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.ui.model.PracticeVO

class InvoiceAdapter (private var invoicesList: List<InvoiceModelRoom>, private val onCLickListener: (InvoiceModelRoom) -> Unit): RecyclerView.Adapter<InvoiceViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceViewHolder(layoutInflater.inflate(R.layout.item_facturas, parent, false))
    }

    override fun getItemCount(): Int {
        return invoicesList.size
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val item = invoicesList[position]
        holder.render(item, onCLickListener)
    }


}