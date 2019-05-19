package com.gg.simplenumbers.di;

import com.gg.simplenumbers.presentation.numberslist.NumbersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NumbersListViewModel(get(),get()) }
}