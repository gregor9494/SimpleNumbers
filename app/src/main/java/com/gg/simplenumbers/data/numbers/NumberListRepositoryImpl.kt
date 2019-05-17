package com.gg.simplenumbers.data.numbers

import com.gg.simplenumbers.domain.LoadMoreResult
import com.gg.simplenumbers.domain.numbers.NumbersListRepository
import io.reactivex.Observable
import io.reactivex.Single

class NumberListRepositoryImpl(
    private val numbersDataSource: NumbersDataSource,
    private val numbersCache: NumbersCache
) : NumbersListRepository {

    override fun getNumbersList(): Observable<List<Int>> = numbersCache.observeNumbersList()

    override fun loadMoreNumbers(): Single<LoadMoreResult> {
        return numbersDataSource.getPage(numbersCache.page)
            .map {
                if (it.isNotEmpty()) {
                    numbersCache.addPage(it)
                    LoadMoreResult.Success
                } else return@map LoadMoreResult.NoMore
            }.onErrorReturnItem(LoadMoreResult.Error)
    }
}