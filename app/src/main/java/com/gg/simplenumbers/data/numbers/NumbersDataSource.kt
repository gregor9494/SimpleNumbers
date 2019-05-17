package com.gg.simplenumbers.data.numbers

import io.reactivex.Single

class NumbersDataSource {
    var pageSize = 30

    var list = generateNumbers()

    private fun generateNumbers(): MutableList<Int> {
        val list = mutableListOf<Int>()
        for (i in 2..10_000 step 2) {
            list.add(i)
        }
        return list
    }

    fun getPage(page: Int): Single<List<Int>> {
        return Single.just(list.subList(page * pageSize, page * pageSize + pageSize))
    }
}