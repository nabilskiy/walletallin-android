package com.example.coinsliberty

import com.example.coinsliberty.di.appModule
import com.example.coinsliberty.di.navigatorsModule
import com.example.coinsliberty.di.sharedModule
import com.example.coinsliberty.di.viewModelModule
import org.koin.android.ext.android.startKoin

object KoinProvider {

    @JvmStatic
    fun startKoin(app: CoinsLibertyApp) {
        app.startKoin(app, listOf(appModule, viewModelModule, navigatorsModule, sharedModule))
    }

}

