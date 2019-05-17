package com.gg.simplenumbers.domain.numbers

import com.gg.simplenumbers.domain.LoadMoreResult
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoadMoreNumbersUseCase(
    private val backgroundExecutor: Scheduler = Schedulers.io(),
    private val resultExecutionThread: Scheduler = AndroidSchedulers.mainThread(),
    private val numbersListRepository: NumbersListRepository
) {
    fun loadMoreNumbers(): Single<LoadMoreResult> = numbersListRepository.loadMoreNumbers()
        .subscribeOn(backgroundExecutor)
        .observeOn(resultExecutionThread)
}