package com.gg.simplenumbers.presentation.numberslist

import androidx.lifecycle.MutableLiveData
import com.gg.simplenumbers.domain.numbers.GetSortedNumbersListUseCase
import com.gg.simplenumbers.presentation.base.BaseViewModel

class NumbersListViewModel(private val getNumbersListUseCase: GetSortedNumbersListUseCase) : BaseViewModel() {

    val numberList = MutableLiveData<MutableList<Int>>().apply { value = mutableListOf() }

    fun observeData() {
        addSubscription(
            getNumbersListUseCase.getNumbersList()
                .subscribe({
                    numberList.value?.addAll(it)
                }, { it.printStackTrace() })
        )
    }

}