package com.gg.simplenumbers.ui.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ggaworowski on 18.05.2019.
 */
object RecyclerViewBinding {
    @BindingAdapter("loadMoreListener")
    @JvmStatic
    fun setLoadMoreListener(
        recyclerView: RecyclerView,
        loadMoreListener: RecyclerViewScrollListener.OnLoadMoreListener
    ) {
        recyclerView.addOnScrollListener(
            RecyclerViewScrollListener(
                recyclerView.layoutManager as LinearLayoutManager,
                loadMoreListener
            )
        )
    }
}