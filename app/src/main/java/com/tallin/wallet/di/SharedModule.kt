package com.tallin.wallet.di

import com.tallin.wallet.model.SharedPreferencesProvider
import org.koin.dsl.module

val sharedModule = module(override = true) {
    single { SharedPreferencesProvider(get()) }
}