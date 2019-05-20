package com.gg.simplenumbers.app

import android.app.Application
import com.gg.simplenumbers.BuildConfig
import com.gg.simplenumbers.di.KoinModules
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by ggaworowski on 17.05.2019.
 */
class SimpleNumberApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        this.initializeLeakDetection()
        this.initInjection()
    }

    private fun initInjection() = startKoin {
        androidContext(this@SimpleNumberApplication)
        modules(KoinModules.modules)
    }

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}