package com.example.coinsliberty

import android.app.Application

class CoinsLibertyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinProvider.startKoin(this)
    }
}