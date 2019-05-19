package com.gg.simplenumbers.presentation.numberslist.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ggaworowski on 18.05.2019.
 */
object NumberListBinding {
    @BindingAdapter("numberListItems")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, list: List<Int>?) {
        with(recyclerView.adapter as NumbersAdapter) {
            list?.let { updateData(it) }
        }
    }


}