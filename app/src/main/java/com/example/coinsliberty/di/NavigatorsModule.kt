package com.example.coinsliberty.di

import com.example.moneybee.utils.stub.StubNavigator
import org.koin.dsl.module

val navigatorsModule = module {
    factory { StubNavigator() }
}