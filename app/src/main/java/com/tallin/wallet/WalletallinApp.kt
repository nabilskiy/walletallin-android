package com.tallin.wallet
import android.app.Application

class WalletallinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinProvider.startKoin(this)
    }


}