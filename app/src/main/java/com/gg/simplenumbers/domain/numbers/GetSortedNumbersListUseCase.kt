package com.gg.simplenumbers.domain.numbers

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetSortedNumbersListUseCase(
    private val backgroundExecutor: Scheduler = Schedulers.io(),
    private val resultExecutionThread: Scheduler = AndroidSchedulers.mainThread(),
    private val numbersListRepository: NumbersListRepository
) {
    fun getNumbersList(): Observable<List<Int>> = numbersListRepository.getNumbersList()
        .subscribeOn(backgroundExecutor)
        .map { it.sorted() }
        .observeOn(resultExecutionThread)
}