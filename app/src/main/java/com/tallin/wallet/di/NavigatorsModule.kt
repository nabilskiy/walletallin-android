package com.tallin.wallet.di

import com.tallin.wallet.ui.exchange.ExchangeNavigator
import com.tallin.wallet.ui.login.LoginNavigation
import com.tallin.wallet.ui.profile.ProfileNavigation
import com.tallin.wallet.ui.settings.SettingsNavigation
import com.tallin.wallet.ui.singup.signup.SignUpNavigation
import com.tallin.wallet.ui.splash.SplashNavigation
import com.tallin.moneybee.utils.stub.StubNavigator
import com.tallin.wallet.dialogs.forgetPassword.ForgotPassNavigator
import com.tallin.wallet.ui.pin.PinNavigation
import com.tallin.wallet.ui.processKYC.KYCProcessNavigation
import com.tallin.wallet.ui.singup.chooseWallet.SingUpChooseWalletNavigation
import com.tallin.wallet.ui.singup.singupBusiness.SingUpBusinessNavigation
import org.koin.dsl.module


val navigatorsModule = module {
    factory { StubNavigator() }

    factory { LoginNavigation() }
    factory { ProfileNavigation() }
    factory { SettingsNavigation() }
    factory { SignUpNavigation() }
    factory { SingUpBusinessNavigation() }
    factory { SingUpChooseWalletNavigation() }
    factory { KYCProcessNavigation() }
    factory { ForgotPassNavigator() }
    factory { SplashNavigation() }
    factory { ExchangeNavigator() }
    factory { PinNavigation() }
}