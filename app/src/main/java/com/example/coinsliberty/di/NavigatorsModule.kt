package com.example.coinsliberty.di

import com.example.moneybee.utils.stub.StubNavigator
import org.koin.dsl.module
//import org.koin.dsl.module.module

val navigatorsModule = module {
    factory { StubNavigator() }
}