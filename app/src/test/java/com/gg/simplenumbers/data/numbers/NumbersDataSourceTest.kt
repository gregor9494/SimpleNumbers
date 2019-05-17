package com.gg.simplenumbers.data.numbers

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class NumbersDataSourceTest {
    lateinit var numbersDataSource: NumbersDataSource

    @Before
    fun setup(){
        numbersDataSource = NumbersDataSource()
    }

    @Test
    fun `test page content is correct`(){
        numbersDataSource.pageSize=2
        numbersDataSource.list = mutableListOf(1,2,3,4,5,6)

        val firstPageContent = numbersDataSource.getPage(0).blockingGet()
        val secondPageContent = numbersDataSource.getPage(1).blockingGet()

        firstPageContent shouldEqual listOf(1,2)
        secondPageContent shouldEqual listOf(3,4)
    }
}