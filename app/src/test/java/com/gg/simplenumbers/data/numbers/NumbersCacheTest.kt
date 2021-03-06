package com.gg.simplenumbers.data.numbers

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class NumbersCacheTest {
    lateinit var numbersCache: NumbersCache

    @Before
    fun setup() {
        numbersCache = NumbersCache()
    }

    @Test
    fun `test add items will increase page number`() {
        numbersCache.page = 0

        numbersCache.addPage(listOf(1))

        numbersCache.page shouldEqual 1
    }

    @Test
    fun `test add new page will add new items`() {
        numbersCache.addPage(listOf(1))
        val listTestObserver = numbersCache.observeNumbersList().test()

        numbersCache.addPage(listOf(2))

        listTestObserver.values().last() shouldEqual listOf(1, 2)
    }

    @Test
    fun `test add number that already exist value`() {
        val number = 2
        numbersCache.addPage(listOf(2, 4, 6))

        val numberAdded = numbersCache.addNumber(number)

        numberAdded shouldEqual false
    }

    @Test
    fun `test add number that does not exist value`() {
        val number = 2
        numbersCache.addPage(listOf(4, 6))

        val numberAdded = numbersCache.addNumber(number)

        numberAdded shouldEqual true
    }
}