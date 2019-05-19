package com.gg.simplenumbers.ui.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by ggaworowski on 17.05.2019.
 */
class RecyclerViewScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val listener: OnLoadMoreListener?,
    private val visibleTreshold: Int = 2
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = linearLayoutManager.itemCount
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

        if (totalItemCount <= lastVisibleItem + visibleTreshold && totalItemCount != 0
            && listener?.canLoadMore() == true
        )
            listener.onLoadMore()

    }

    interface OnLoadMoreListener {
        fun onLoadMore()
        fun canLoadMore(): Boolean
    }

}