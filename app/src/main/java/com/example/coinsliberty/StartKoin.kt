package com.example.coinsliberty

import com.example.coinsliberty.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


object KoinProvider {

    @JvmStatic
    fun startKoin(app: CoinsLibertyApp) {
        startKoin {
            androidContext(app)
            modules(listOf(appModule, viewModelModule, navigatorsModule, sharedModule, retrofitModule, repositoryModule))
        }
    }
}
