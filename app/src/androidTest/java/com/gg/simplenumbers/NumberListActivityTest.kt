package com.gg.simplenumbers

import android.content.Intent
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
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
import com.gg.simplenumbers.presentation.numberslist.NumbersListViewModel
import com.gg.simplenumbers.presentation.numberslist.list.NumberViewHolder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
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
    val loadMoreDelay = 300L

    val testRepositoryModule = module {
        single { NumberListRepositoryImpl(numbersDataSource, numbersCache) as NumbersListRepository }
    }

    val testViewModelModule = module {
        viewModel { NumbersListViewModel(get(), get(), loadMoreDelay-100L) }
    }

    @Before
    fun setup() {

        numbersDataSource.pageSize = pageSize
        numbersDataSource.list = data
        loadKoinModules(testViewModelModule, useCaseModule, testRepositoryModule)
    }

    @Test
    fun testScrollWillLoadMoreItems() {
        activityTestRule.launchActivity(Intent())

        SystemClock.sleep(loadMoreDelay)
        onView(withId(R.id.numbersList)).perform(RecyclerViewActions.scrollToPosition<NumberViewHolder>(pageSize - 1))
        SystemClock.sleep(loadMoreDelay)
        onView(withId(R.id.numbersList)).check(RecyclerViewItemCountAssertion(pageSize * 2))

    }

    @Test
    fun testListIsEmptyWhenNoData() {
        numbersDataSource.list = mutableListOf()
        numbersCache.clear()

        activityTestRule.launchActivity(Intent())

        SystemClock.sleep(loadMoreDelay)
        onView(withId(R.id.numbersList)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun testLoadMoreWillShowAfterScrollToBottom() {
        activityTestRule.launchActivity(Intent())

        SystemClock.sleep(loadMoreDelay)
        onView(withId(R.id.numbersList)).check(RecyclerViewItemCountAssertion(pageSize))
        onView(withId(R.id.numbersList)).perform(RecyclerViewActions.scrollToPosition<NumberViewHolder>(pageSize - 1))
        onView(withId(R.id.numbersList)).check(RecyclerViewItemCountAssertion(pageSize + 1))
    }

    @After
    fun tearDown() {
        unloadKoinModules(viewModelModule, useCaseModule, testRepositoryModule)
    }

}