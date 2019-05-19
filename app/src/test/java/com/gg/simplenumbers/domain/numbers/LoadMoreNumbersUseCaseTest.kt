package com.gg.simplenumbers.domain.numbers

import com.gg.simplenumbers.UnitTest
import com.gg.simplenumbers.domain.LoadMoreResult
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.mockito.Mock
import java.util.concurrent.TimeUnit

class LoadMoreNumbersUseCaseTest : UnitTest() {

    lateinit var loadMoreNumbersUseCase: LoadMoreNumbersUseCase

    @Mock
    lateinit var numbersListRepository: NumbersListRepository

    @Test
    fun `test use case will return correct value`() {
        whenever(numbersListRepository.loadMoreNumbers()).thenReturn(Single.just(LoadMoreResult.Success))
        loadMoreNumbersUseCase = LoadMoreNumbersUseCase(
            backgroundExecutor = Schedulers.trampoline(),
            resultExecutionThread = Schedulers.trampoline(),
            numbersListRepository = numbersListRepository
        )

        val getNumbersListRequest = loadMoreNumbersUseCase.loadMoreNumbers(0)
        val test = getNumbersListRequest.test()

        test.assertValue(LoadMoreResult.Success)
        test.assertValueCount(1)
        test.assertComplete()
    }

    @Test
    fun `test use case will execute after delay if needed`() {
        whenever(numbersListRepository.loadMoreNumbers()).thenReturn(Single.just(LoadMoreResult.Success))
        val testScheduler = TestScheduler()
        loadMoreNumbersUseCase = LoadMoreNumbersUseCase(
            backgroundExecutor = testScheduler,
            resultExecutionThread = Schedulers.trampoline(),
            numbersListRepository = numbersListRepository
        )

        val getNumbersListRequest = loadMoreNumbersUseCase.loadMoreNumbers(2000)
        val test = getNumbersListRequest.test()

        test.assertNoValues()
        testScheduler.advanceTimeTo(2500, TimeUnit.MILLISECONDS)
        test.assertValue(LoadMoreResult.Success)
        test.assertValueCount(1)
        test.assertComplete()
    }
}