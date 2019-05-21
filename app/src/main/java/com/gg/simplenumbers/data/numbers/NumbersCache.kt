package com.gg.simplenumbers.data.numbers

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class NumbersCache {
    private val list = BehaviorSubject.createDefault<List<Int>>(emptyList())
    var page = 0

    fun addPage(numbers: List<Int>) {
        val newList = mutableListOf<Int>()
        list.value?.forEach { if (!newList.contains(it)) newList.add(it) }
        numbers.forEach { if (!newList.contains(it)) newList.add(it) }
        list.onNext(newList)
        page++
    }

    fun addNumber(number: Int) : Boolean{
        if (list.value?.contains(number) == false) {
            list.onNext((list.value ?: emptyList()).plus(number))
            return true
        }
        return false
    }

    fun observeNumbersList(): Observable<List<Int>> = list

    fun clear() {
        list.onNext(emptyList())
    }
}