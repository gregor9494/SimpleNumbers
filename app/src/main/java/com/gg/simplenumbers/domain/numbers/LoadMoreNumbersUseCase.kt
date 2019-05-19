package com.gg.simplenumbers.domain.numbers

import com.gg.simplenumbers.domain.LoadMoreResult
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoadMoreNumbersUseCase(
    private val backgroundExecutor: Scheduler = Schedulers.io(),
    private val resultExecutionThread: Scheduler = AndroidSchedulers.mainThread(),
    private val numbersListRepository: NumbersListRepository
) {
    // Delay is just for better looking pagination
    fun loadMoreNumbers(delayMilliseconds: Long = 0): Single<LoadMoreResult> =
        numbersListRepository.loadMoreNumbers()
            .delaySubscription(delayMilliseconds, TimeUnit.MILLISECONDS,backgroundExecutor)
            .subscribeOn(backgroundExecutor)
            .observeOn(resultExecutionThread)
}