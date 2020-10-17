package com.example.coinsliberty

import com.example.coinsliberty.di.appModule
import com.example.coinsliberty.di.navigatorsModule
import com.example.coinsliberty.di.sharedModule
import com.example.coinsliberty.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


object KoinProvider {

    @JvmStatic
    fun startKoin(app: CoinsLibertyApp) {
        startKoin {
            androidContext(app)
            modules(listOf(appModule, viewModelModule, navigatorsModule, sharedModule))
        }
    }
}
