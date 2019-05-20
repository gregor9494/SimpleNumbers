package com.gg.simplenumbers.presentation.numberslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gg.simplenumbers.UnitTest
import com.gg.simplenumbers.domain.LoadMoreResult
import com.gg.simplenumbers.domain.numbers.GetSortedNumbersListUseCase
import com.gg.simplenumbers.domain.numbers.LoadMoreNumbersUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class NumbersListViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var numbersListViewModel: NumbersListViewModel

    @Mock
    lateinit var getNumbersListUseCase: GetSortedNumbersListUseCase

    @Mock
    lateinit var loadMoreNumbersUseCase: LoadMoreNumbersUseCase

    @Test
    fun `test view model will add new items to list after load`() {
        val list = listOf(1)
        whenever(getNumbersListUseCase.getNumbersList()).thenReturn(Observable.just(list))
        whenever(loadMoreNumbersUseCase.loadMoreNumbers(any())).thenReturn(Single.never())
        numbersListViewModel = NumbersListViewModel(getNumbersListUseCase, loadMoreNumbersUseCase)

        numbersListViewModel.observeData()

        numbersListViewModel.numberList.value shouldEqual list
    }

    @Test
    fun `test can load more is false when is case return no more items`() {
        val list = listOf(1)
        whenever(getNumbersListUseCase.getNumbersList()).thenReturn(Observable.just(list))
        whenever(loadMoreNumbersUseCase.loadMoreNumbers(any())).thenReturn(Single.just(LoadMoreResult.NoMore))
        numbersListViewModel = NumbersListViewModel(getNumbersListUseCase, loadMoreNumbersUseCase)

        numbersListViewModel.onLoadMore()

        numbersListViewModel.canLoadMore() shouldEqual false
    }

}