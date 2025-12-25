package com.bura.transparent.accounts

import android.app.Application
import com.bura.transparent.accounts.di.AppGraph
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(AppGraph.module)
        }
    }
}