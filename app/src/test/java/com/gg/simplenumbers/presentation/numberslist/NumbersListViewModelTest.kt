package com.gg.simplenumbers.presentation.numberslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gg.simplenumbers.R
import com.gg.simplenumbers.UnitTest
import com.gg.simplenumbers.domain.LoadMoreResult
import com.gg.simplenumbers.domain.numbers.AddNewNumberUseCase
import com.gg.simplenumbers.domain.numbers.GetSortedNumbersListUseCase
import com.gg.simplenumbers.domain.numbers.LoadMoreNumbersUseCase
import com.gg.simplenumbers.presentation.common.Event
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.amshove.kluent.shouldBe
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

    @Mock
    lateinit var addNewNumberUseCase: AddNewNumberUseCase

    @Test
    fun `test view model will add new items to list after load`() {
        val list = listOf(1)
        whenever(getNumbersListUseCase.getNumbersList()).thenReturn(Observable.just(list))
        whenever(loadMoreNumbersUseCase.loadMoreNumbers(any())).thenReturn(Single.never())
        numbersListViewModel = NumbersListViewModel(getNumbersListUseCase, loadMoreNumbersUseCase, addNewNumberUseCase)

        numbersListViewModel.observeData()

        numbersListViewModel.numberList.value shouldEqual list
    }

    @Test
    fun `test can load more is false when is case return no more items`() {
        val list = listOf(1)
        whenever(getNumbersListUseCase.getNumbersList()).thenReturn(Observable.just(list))
        whenever(loadMoreNumbersUseCase.loadMoreNumbers(any())).thenReturn(Single.just(LoadMoreResult.NoMore))
        numbersListViewModel = NumbersListViewModel(getNumbersListUseCase, loadMoreNumbersUseCase, addNewNumberUseCase)

        numbersListViewModel.onLoadMore()

        numbersListViewModel.canLoadMore() shouldEqual false
    }

    @Test
    fun `test error message on add numberwhen wrong number entered`() {
        val number = null
        whenever(getNumbersListUseCase.getNumbersList()).thenReturn(Observable.just(emptyList()))
        whenever(loadMoreNumbersUseCase.loadMoreNumbers(any())).thenReturn(Single.just(LoadMoreResult.NoMore))
        numbersListViewModel = NumbersListViewModel(getNumbersListUseCase, loadMoreNumbersUseCase, addNewNumberUseCase)

        numbersListViewModel.addNewNumber(number)

        numbersListViewModel.errorMessage.value shouldEqual Event(R.string.error_message_wrong_number)
    }

    @Test
    fun `test error message on add number when number already exist`() {
        val number = 2
        whenever(getNumbersListUseCase.getNumbersList()).thenReturn(Observable.just(emptyList()))
        whenever(loadMoreNumbersUseCase.loadMoreNumbers(any())).thenReturn(Single.just(LoadMoreResult.NoMore))
        whenever(addNewNumberUseCase.addNewNumber(2)).thenReturn(false)
        numbersListViewModel = NumbersListViewModel(getNumbersListUseCase, loadMoreNumbersUseCase, addNewNumberUseCase)

        numbersListViewModel.addNewNumber(number)

        numbersListViewModel.errorMessage.value shouldEqual Event(R.string.error_message_number_exist)
    }
}