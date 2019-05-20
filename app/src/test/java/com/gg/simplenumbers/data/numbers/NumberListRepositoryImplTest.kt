package com.gg.simplenumbers.data.numbers

import com.gg.simplenumbers.UnitTest
import com.gg.simplenumbers.domain.LoadMoreResult
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class NumberListRepositoryImplTest : UnitTest() {
    lateinit var numberListRepositoryImpl: NumberListRepositoryImpl

    @Mock
    lateinit var numbersDataSource: NumbersDataSource

    @Mock
    lateinit var numbersCache: NumbersCache

    @Before
    fun setup() {
        numberListRepositoryImpl = NumberListRepositoryImpl(numbersDataSource, numbersCache)
    }

    @Test
    fun `test load more when there is more items`() {
        val page = listOf(1, 2, 3, 4)
        whenever(numbersDataSource.getPage(any())).thenReturn(Single.just(page))

        val result = numberListRepositoryImpl.loadMoreNumbers().test()

        result.assertValue(LoadMoreResult.Success)
        result.assertValueCount(1)
    }

    @Test
    fun `test load more when there is no more items`() {
        val page = emptyList<Int>()
        whenever(numbersDataSource.getPage(any())).thenReturn(Single.just(page))

        val result = numberListRepositoryImpl.loadMoreNumbers().test()

        result.assertValue(LoadMoreResult.NoMore)
        result.assertValueCount(1)
    }

    @Test
    fun `test numbers will come from cache`() {
        val page = listOf(1, 2, 3, 4)
        whenever(numbersCache.observeNumbersList()).thenReturn(Observable.just(page))

        val result = numberListRepositoryImpl.getNumbersList().test()

        result.assertValue(page)
        result.assertValueCount(1)
    }
}