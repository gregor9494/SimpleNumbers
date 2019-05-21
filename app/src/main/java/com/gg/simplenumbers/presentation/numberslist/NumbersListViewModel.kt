package com.gg.simplenumbers.presentation.numberslist

import androidx.lifecycle.MutableLiveData
import com.gg.simplenumbers.R
import com.gg.simplenumbers.domain.LoadMoreResult
import com.gg.simplenumbers.domain.numbers.AddNewNumberUseCase
import com.gg.simplenumbers.domain.numbers.GetSortedNumbersListUseCase
import com.gg.simplenumbers.domain.numbers.LoadMoreNumbersUseCase
import com.gg.simplenumbers.presentation.base.BaseViewModel
import com.gg.simplenumbers.presentation.common.Event
import com.gg.simplenumbers.ui.list.RecyclerViewScrollListener

class NumbersListViewModel(
    private val getNumbersListUseCase: GetSortedNumbersListUseCase,
    private val loadMoreNumbersUseCase: LoadMoreNumbersUseCase,
    private val addNewNumberUseCase: AddNewNumberUseCase,
    private val loadMoreDelayMilliseconds: Long = 2000 // Delay is just for better looking pagination
) : BaseViewModel(), RecyclerViewScrollListener.OnLoadMoreListener {

    private var canLoadMore: Boolean = true
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val numberList = MutableLiveData<List<Int>>().apply { value = emptyList() }
    val errorMessage = MutableLiveData<Event<Int>>()

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
            loadMoreNumbersUseCase.loadMoreNumbers(loadMoreDelayMilliseconds)
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

    fun addNewNumber(number: Int?) {
        if (number == null) {
            errorMessage.value = Event(R.string.error_message_wrong_number)
        } else if (!addNewNumberUseCase.addNewNumber(number)) {
            errorMessage.value = Event(R.string.error_message_number_exist)
        }
    }

}