package com.mvvmdemo

import android.app.Application
import com.mvvmdemo.di.networkModule
import com.mvvmdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application(){

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        initializeKoin()

    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }

}