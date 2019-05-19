package com.gg.simplenumbers.presentation.numberslist.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gg.simplenumbers.databinding.NumberItemBinding

class NumberViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    private val binding = NumberItemBinding.bind(parent)

    fun bindTo(number: NumberListItem.NumberItem) {
        binding.numberItem = number
    }
}