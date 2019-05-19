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
        val list =
            when {
                (page + 1) * pageSize < list.size -> list.subList(page * pageSize, (page + 1) * pageSize)
                page * pageSize < list.size  -> list.subList(page * pageSize, list.size)
                else -> emptyList<Int>()
            }
        return Single.just(list)
    }
}