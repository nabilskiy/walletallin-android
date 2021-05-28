package com.tallin.wallet

import com.tallin.wallet.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


object KoinProvider {

    @JvmStatic
    fun startKoin(app: WalletallinApp) {
        startKoin {
            androidContext(app)
            modules(listOf(appModule, viewModelModule, navigatorsModule, sharedModule, retrofitModule, repositoryModule))
        }
    }
}
