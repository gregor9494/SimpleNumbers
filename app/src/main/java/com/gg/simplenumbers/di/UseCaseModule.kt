package com.gg.simplenumbers.di;

import com.gg.simplenumbers.domain.numbers.AddNewNumberUseCase
import com.gg.simplenumbers.domain.numbers.GetSortedNumbersListUseCase
import com.gg.simplenumbers.domain.numbers.LoadMoreNumbersUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetSortedNumbersListUseCase(numbersListRepository = get()) }
    single { LoadMoreNumbersUseCase(numbersListRepository = get()) }
    single { AddNewNumberUseCase(get()) }
}