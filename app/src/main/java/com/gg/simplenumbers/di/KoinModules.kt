package com.gg.simplenumbers.di;


object KoinModules {
    val modules = listOf(
        viewModelModule,
        repositoryModule,
        useCaseModule
    )
}