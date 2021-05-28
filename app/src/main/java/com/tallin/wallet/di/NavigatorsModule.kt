package com.tallin.wallet.di

import com.tallin.wallet.ui.exchange.ExchangeNavigator
import com.tallin.wallet.ui.login.LoginNavigation
import com.tallin.wallet.ui.profile.ProfileNavigation
import com.tallin.wallet.ui.settings.SettingsNavigation
import com.tallin.wallet.ui.signup.SignUpNavigation
import com.tallin.wallet.ui.splash.SplashNavigation
import com.tallin.moneybee.utils.stub.StubNavigator
import com.tallin.wallet.dialogs.forgetPassword.ForgotPassNavigator
import com.tallin.wallet.ui.pin.PinNavigation
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