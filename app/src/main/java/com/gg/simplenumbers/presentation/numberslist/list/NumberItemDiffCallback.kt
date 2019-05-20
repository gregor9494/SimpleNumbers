package com.gg.simplenumbers.presentation.numberslist.list

import androidx.recyclerview.widget.DiffUtil

class NumberItemDiffCallback(
    private val oldList: List<NumberListItem>,
    private val newList: List<NumberListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
}