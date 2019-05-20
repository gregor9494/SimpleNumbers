package com.gg.simplenumbers.di;

import com.gg.simplenumbers.data.numbers.NumberListRepositoryImpl
import com.gg.simplenumbers.data.numbers.NumbersCache
import com.gg.simplenumbers.data.numbers.NumbersDataSource
import com.gg.simplenumbers.domain.numbers.NumbersListRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { NumberListRepositoryImpl(NumbersDataSource(), NumbersCache()) as NumbersListRepository }
}