package com.example.practicalistadofacturasfinal.ui.model.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.practicalistadofacturasfinal.databinding.ItemPracticeSelectionBinding
import com.example.practicalistadofacturasfinal.ui.model.PracticeVO

class PracticeViewHolder (view: View): ViewHolder(view) {
    val binding = ItemPracticeSelectionBinding.bind(view)

    fun render (item: PracticeVO, onClickListener: (PracticeVO) -> Unit) {
        binding.itemTvSelection.text = item.title

        itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}
