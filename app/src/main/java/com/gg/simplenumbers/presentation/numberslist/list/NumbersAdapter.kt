package com.gg.simplenumbers.presentation.numberslist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gg.simplenumbers.R

class NumbersAdapter(val items: MutableList<NumberListItem> = mutableListOf(), var isLoading: Boolean = false) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_ITEM) {
            NumberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.number_item, parent, false))
        } else {
            LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false))
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (items[position] is NumberListItem.NumberItem)
            (holder as NumberViewHolder).bindTo(items[position] as NumberListItem.NumberItem)
    }

    override fun getItemViewType(position: Int): Int =
        if (items[position] is NumberListItem.NumberItem) VIEW_TYPE_ITEM
        else VIEW_TYPE_LOADING

    fun setLoadingMore(loading: Boolean) {
        if (this.isLoading == loading) return
        this.isLoading = loading
        if (!loading && items.contains(NumberListItem.LoadMoreItem)) {
            items.remove(NumberListItem.LoadMoreItem)
            notifyItemRemoved(items.size)
        } else if (!items.contains(NumberListItem.LoadMoreItem)) {
            items.add(NumberListItem.LoadMoreItem)
            notifyItemInserted(items.size - 1)
        }
    }

    fun updateData(data: List<Int>) {
        val items: MutableList<NumberListItem> = data.map { NumberListItem.NumberItem(it) }.toMutableList()
        if (isLoading) items.add(NumberListItem.LoadMoreItem)
        updateList(items)
    }

    private fun updateList(newItems: List<NumberListItem>) {
        // Just little workaround for scrolling to new loader item position
        this.items.indexOf(NumberListItem.LoadMoreItem).takeIf { it != -1 }?.let {
            this.items.removeAt(it)
            notifyItemRemoved(it)
        }

        val diffCallback = NumberItemDiffCallback(this.items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items.clear()
        this.items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}