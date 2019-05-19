package com.gg.simplenumbers.presentation.numberslist.list

import com.gg.simplenumbers.AndroidTest
import com.gg.simplenumbers.UnitTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config

/**
 * Created by ggaworowski on 18.05.2019.
 */
class NumbersAdapterTest : AndroidTest() {
    lateinit var numbersAdapter: NumbersAdapter

    @Before
    fun setup(){
        numbersAdapter = NumbersAdapter()
    }

    @Test
    fun `test list will have all items`() {
        numbersAdapter.setLoadingMore(false)
        val data = listOf(1, 2, 3, 4, 5, 6)

        numbersAdapter.updateData(data)

        data.map { NumberListItem.NumberItem(it) } shouldEqual numbersAdapter.items
    }

    @Test
    fun `test list will add loading item after set loading to true`() {
        numbersAdapter.setLoadingMore(false)
        val data = listOf(1, 2, 3, 4, 5, 6)

        numbersAdapter.updateData(data)
        numbersAdapter.setLoadingMore(true)

        numbersAdapter.items.contains(NumberListItem.LoadMoreItem) shouldEqual true
    }

    @Test
    fun `test list will remove loading item after set loading to false`() {
        numbersAdapter.setLoadingMore(true)
        val data = listOf(1, 2, 3, 4, 5, 6)

        numbersAdapter.updateData(data)
        numbersAdapter.setLoadingMore(false)

        numbersAdapter.items.contains(NumberListItem.LoadMoreItem) shouldEqual false
    }

    @Test
    fun `test has loading view when is loading and no data list is empty`() {
        val data = emptyList<Int>()

        numbersAdapter.updateData(data)
        numbersAdapter.setLoadingMore(true)

        numbersAdapter.items shouldEqual listOf(NumberListItem.LoadMoreItem)
    }
}