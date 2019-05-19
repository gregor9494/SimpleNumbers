package com.gg.simplenumbers.presentation.numberslist

import androidx.lifecycle.MutableLiveData
import com.gg.simplenumbers.domain.LoadMoreResult
import com.gg.simplenumbers.domain.numbers.GetSortedNumbersListUseCase
import com.gg.simplenumbers.domain.numbers.LoadMoreNumbersUseCase
import com.gg.simplenumbers.presentation.base.BaseViewModel
import com.gg.simplenumbers.ui.list.RecyclerViewScrollListener

class NumbersListViewModel(
    private val getNumbersListUseCase: GetSortedNumbersListUseCase,
    private val loadMoreNumbersUseCase: LoadMoreNumbersUseCase
) : BaseViewModel(), RecyclerViewScrollListener.OnLoadMoreListener {

    private var canLoadMore: Boolean = true
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val numberList = MutableLiveData<List<Int>>().apply { value = emptyList() }

    init {
        observeData()
        onLoadMore()
    }

    fun observeData() {
        addSubscription(
            getNumbersListUseCase.getNumbersList()
                .subscribe({
                    numberList.value = it
                }, { it.printStackTrace() })
        )
    }

    override fun onLoadMore() {
        if (isLoading.value == true) return
        isLoading.value = true
        addSubscription(
            loadMoreNumbersUseCase.loadMoreNumbers(2000)
                .subscribe({
                    isLoading.value = false
                    canLoadMore = it !is LoadMoreResult.NoMore
                }, {
                    isLoading.value = false
                    it.printStackTrace()
                })
        )
    }

    override fun canLoadMore(): Boolean = canLoadMore

}