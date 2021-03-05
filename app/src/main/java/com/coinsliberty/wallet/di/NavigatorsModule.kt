package com.coinsliberty.wallet.di

import com.coinsliberty.wallet.ui.exchange.ExchangeNavigator
import com.coinsliberty.wallet.ui.login.LoginNavigation
import com.coinsliberty.wallet.ui.profile.ProfileNavigation
import com.coinsliberty.wallet.ui.settings.SettingsNavigation
import com.coinsliberty.wallet.ui.signup.SignUpNavigation
import com.coinsliberty.wallet.ui.splash.SplashNavigation
import com.coinsliberty.moneybee.utils.stub.StubNavigator
import com.coinsliberty.wallet.dialogs.forgetPassword.ForgotPassNavigator
import com.coinsliberty.wallet.ui.pin.PinNavigation
import org.koin.dsl.module


val navigatorsModule = module {
    factory { StubNavigator() }

    factory { LoginNavigation() }
    factory { ProfileNavigation() }
    factory { SettingsNavigation() }
    factory { SignUpNavigation() }
    factory { ForgotPassNavigator() }
    factory { SplashNavigation() }
    factory { ExchangeNavigator() }
    factory { PinNavigation() }
}