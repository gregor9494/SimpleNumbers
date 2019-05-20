package com.gg.simplenumbers.domain.numbers

import com.gg.simplenumbers.domain.LoadMoreResult
import io.reactivex.Observable
import io.reactivex.Single

interface NumbersListRepository {
    fun getNumbersList() : Observable<List<Int>>
    fun loadMoreNumbers() : Single<LoadMoreResult>
}