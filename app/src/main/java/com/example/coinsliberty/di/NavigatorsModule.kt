package com.example.coinsliberty.di

import com.example.coinsliberty.ui.exchange.ExchangeNavigator
import com.example.coinsliberty.ui.login.LoginNavigation
import com.example.coinsliberty.ui.profile.ProfileNavigation
import com.example.coinsliberty.ui.settings.SettingsNavigation
import com.example.coinsliberty.ui.signup.SignUpNavigation
import com.example.moneybee.ui.splash.SplashNavigation
import com.example.moneybee.utils.stub.StubNavigator
import org.koin.dsl.module


val navigatorsModule = module {
    factory { StubNavigator() }

    factory { LoginNavigation() }
    factory { ProfileNavigation() }
    factory { SettingsNavigation() }
    factory { SignUpNavigation() }
    factory { SplashNavigation() }
    factory { ExchangeNavigator() }
}