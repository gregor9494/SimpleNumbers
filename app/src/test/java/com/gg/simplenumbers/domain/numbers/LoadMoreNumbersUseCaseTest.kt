package com.gg.simplenumbers.domain.numbers

import com.gg.simplenumbers.UnitTest
import com.gg.simplenumbers.domain.LoadMoreResult
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class LoadMoreNumbersUseCaseTest : UnitTest() {

    lateinit var loadMoreNumbersUseCase: LoadMoreNumbersUseCase

    @Mock
    lateinit var numbersListRepository: NumbersListRepository

    @Before
    fun setup() {

    }

    @Test
    fun `test use case will return correct value`() {
        whenever(numbersListRepository.loadMoreNumbers()).thenReturn(Single.just(LoadMoreResult.Success))
        loadMoreNumbersUseCase = LoadMoreNumbersUseCase(
            backgroundExecutor = Schedulers.trampoline(),
            resultExecutionThread = Schedulers.trampoline(),
            numbersListRepository = numbersListRepository
        )

        val getNumbersListRequest = loadMoreNumbersUseCase.loadMoreNumbers()
        val test = getNumbersListRequest.test()

        test.assertValue(LoadMoreResult.Success)
        test.assertValueCount(1)
        test.assertComplete()
    }


}