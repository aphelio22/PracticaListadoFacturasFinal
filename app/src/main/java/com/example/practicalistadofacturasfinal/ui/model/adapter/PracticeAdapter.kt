package com.example.practicalistadofacturasfinal.ui.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.ui.model.PracticeVO

class PracticeAdapter(private var practiceList: List<PracticeVO>, private val onCLickListener: (PracticeVO) -> Unit): RecyclerView.Adapter<PracticeViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PracticeViewHolder(layoutInflater.inflate(R.layout.item_practice_selection, parent, false))
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        val item = practiceList[position]
        holder.render(item, onCLickListener)
    }

    override fun getItemCount(): Int {
       return practiceList.size
    }

}