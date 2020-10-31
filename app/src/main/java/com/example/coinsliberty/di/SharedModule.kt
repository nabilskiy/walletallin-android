package com.example.coinsliberty.di

import com.example.coinsliberty.model.SharedPreferencesProvider
import org.koin.dsl.module

val sharedModule = module(override = true) {
    single { SharedPreferencesProvider(get()) }
}