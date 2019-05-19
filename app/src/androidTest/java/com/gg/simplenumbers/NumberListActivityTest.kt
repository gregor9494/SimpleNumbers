package com.gg.simplenumbers

import android.content.Intent
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.gg.simplenumbers.base.assertions.RecyclerViewItemCountAssertion
import com.gg.simplenumbers.data.numbers.NumberListRepositoryImpl
import com.gg.simplenumbers.data.numbers.NumbersCache
import com.gg.simplenumbers.data.numbers.NumbersDataSource
import com.gg.simplenumbers.di.useCaseModule
import com.gg.simplenumbers.di.viewModelModule
import com.gg.simplenumbers.domain.numbers.NumbersListRepository
import com.gg.simplenumbers.presentation.numberslist.NumbersListActivity
import com.gg.simplenumbers.presentation.numberslist.list.NumberViewHolder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * Created by ggaworowski on 18.05.2019.
 */
@LargeTest
class NumberListActivityTest : KoinTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(NumbersListActivity::class.java, true, false)

    val numbersDataSource = NumbersDataSource()
    val numbersCache = NumbersCache()
    val pageSize = 30
    val data = (2..10_000 step 2).toMutableList()

    val testRepositoryModule = module {
        single { NumberListRepositoryImpl(numbersDataSource, numbersCache) as NumbersListRepository }
    }

    @Before
    fun setup() {
        numbersDataSource.pageSize = pageSize
        numbersDataSource.list = data
        loadKoinModules(viewModelModule, useCaseModule, testRepositoryModule)
    }

    @Test
    fun testScrollWillLoadMoreItems() {
        activityTestRule.launchActivity(Intent())
        val pageSize = pageSize
        SystemClock.sleep(2500)
        onView(withId(R.id.numbersList)).perform(RecyclerViewActions.scrollToPosition<NumberViewHolder>(pageSize - 1))
        SystemClock.sleep(2500)
        onView(withId(R.id.numbersList)).check(RecyclerViewItemCountAssertion(pageSize * 2))

    }

    @Test
    fun testListIsEmptyWhenNoData() {
        numbersDataSource.list = mutableListOf()
        numbersCache.clear()

        activityTestRule.launchActivity(Intent())

        SystemClock.sleep(2500) // Wait until progressbar will disappear
        onView(withId(R.id.numbersList)).check(RecyclerViewItemCountAssertion(0))
    }

    @After
    fun tearDown() {
        unloadKoinModules(viewModelModule, useCaseModule, testRepositoryModule)
    }

}