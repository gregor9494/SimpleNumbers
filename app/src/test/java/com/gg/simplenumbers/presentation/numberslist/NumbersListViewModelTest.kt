package com.gg.simplenumbers.presentation.numberslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gg.simplenumbers.UnitTest
import com.gg.simplenumbers.domain.numbers.GetSortedNumbersListUseCase
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
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

    @Before
    fun setup() {
        numbersListViewModel = NumbersListViewModel(getNumbersListUseCase)
    }

    @Test
    fun `view model will add new items to list after load`() {
        numbersListViewModel.numberList.value = mutableListOf()
        val list = listOf(1)
        whenever(getNumbersListUseCase.getNumbersList()).thenReturn(Observable.just(list))

        numbersListViewModel.observeData()

        numbersListViewModel.numberList.value shouldEqual list
    }

}