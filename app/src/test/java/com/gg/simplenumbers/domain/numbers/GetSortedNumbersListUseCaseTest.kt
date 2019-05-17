package com.gg.simplenumbers.domain.numbers

import com.gg.simplenumbers.UnitTest
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetSortedNumbersListUseCaseTest : UnitTest() {

    lateinit var getSortedNumbersListUseCase: GetSortedNumbersListUseCase

    @Mock
    lateinit var numbersListRepository: NumbersListRepository

    @Before
    fun setup() {

    }

    @Test
    fun `test use case will return correct value`() {
        val numbers = (1..20).toList()
        whenever(numbersListRepository.getNumbersList()).thenReturn(Observable.just(numbers))
        getSortedNumbersListUseCase = GetSortedNumbersListUseCase(
            backgroundExecutor = Schedulers.trampoline(),
            resultExecutionThread = Schedulers.trampoline(),
            numbersListRepository = numbersListRepository
        )

        val getNumbersListRequest = getSortedNumbersListUseCase.getNumbersList()
        val test = getNumbersListRequest.test()

        test.assertValue(numbers)
        test.assertValueCount(1)
        test.assertComplete()
    }

    @Test
    fun `test use case will return sorted list`() {
        val numbers = listOf(1,4,3,2,5)
        whenever(numbersListRepository.getNumbersList()).thenReturn(Observable.just(numbers))
        getSortedNumbersListUseCase = GetSortedNumbersListUseCase(
            backgroundExecutor = Schedulers.trampoline(),
            resultExecutionThread = Schedulers.trampoline(),
            numbersListRepository = numbersListRepository
        )

        val getNumbersListRequest = getSortedNumbersListUseCase.getNumbersList()
        val test = getNumbersListRequest.test()

        test.assertValue(listOf(1,2,3,4,5))
        test.assertValueCount(1)
        test.assertComplete()
    }
}