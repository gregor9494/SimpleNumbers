package com.gg.simplenumbers.base

import android.app.Application
import com.gg.simplenumbers.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * We use a separate [Application] for tests to prevent initializing release modules.
 * On the contrary, we will provide inside our tests custom [Module] directly.
 */
class FakeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {}
    }
}