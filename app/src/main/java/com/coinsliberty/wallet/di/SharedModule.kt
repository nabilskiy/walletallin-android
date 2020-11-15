package com.coinsliberty.wallet.di

import com.coinsliberty.wallet.model.SharedPreferencesProvider
import org.koin.dsl.module

val sharedModule = module(override = true) {
    single { SharedPreferencesProvider(get()) }
}