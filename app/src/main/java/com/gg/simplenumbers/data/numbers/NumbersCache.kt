package com.gg.simplenumbers.data.numbers

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class NumbersCache {
    private val list = BehaviorSubject.createDefault<List<Int>>(emptyList())
    var page = 0

    fun addPage(numbers: List<Int>) {
        val newList = list.value!!.plus(numbers)
        list.onNext(newList)
        page++
    }

    fun observeNumbersList(): Observable<List<Int>> = list
}