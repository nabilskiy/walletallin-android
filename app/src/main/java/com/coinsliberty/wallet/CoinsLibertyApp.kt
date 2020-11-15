package com.coinsliberty.wallet

import android.app.Application

class CoinsLibertyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinProvider.startKoin(this)
    }
}