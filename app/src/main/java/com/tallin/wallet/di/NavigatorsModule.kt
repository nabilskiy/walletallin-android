package com.tallin.wallet.di

import com.tallin.wallet.ui.exchange.ExchangeNavigator
import com.tallin.wallet.ui.login.LoginNavigation
import com.tallin.wallet.ui.profile.ProfileNavigation
import com.tallin.wallet.ui.settings.SettingsNavigation
import com.tallin.wallet.ui.singup.signup.SignUpNavigation
import com.tallin.wallet.ui.splash.SplashNavigation
import com.tallin.moneybee.utils.stub.StubNavigator
import com.tallin.wallet.dialogs.forgetPassword.ForgotPassNavigator
import com.tallin.wallet.ui.actions.RateTimer
import com.tallin.wallet.ui.actions.buy.BuySellNavigation
import com.tallin.wallet.ui.actions.orderPreview.OrderPreviewNavigation
import com.tallin.wallet.ui.actions.sell.ConfirmationNavigation
import com.tallin.wallet.ui.actions.verifyPurchase.VerifyPurchaseNavigation
import com.tallin.wallet.ui.fragmentActions.ActionsNavigation
import com.tallin.wallet.ui.kyc.kyc.KycNavigation
import com.tallin.wallet.ui.kyc.manuallyKYC.KYCManuallyNavigation
import com.tallin.wallet.ui.pin.PinNavigation
import com.tallin.wallet.ui.kyc.processKYC.KYCProcessNavigation
import com.tallin.wallet.ui.kyc.webView.KYCWebViewNavigation
import com.tallin.wallet.ui.singup.chooseWallet.SingUpChooseWalletNavigation
import com.tallin.wallet.ui.singup.singupBusiness.SingUpBusinessNavigation
import org.koin.dsl.module


val navigatorsModule = module {
    factory { StubNavigator() }

    factory { LoginNavigation() }
    factory { ProfileNavigation() }
    factory { SettingsNavigation() }
    factory { ActionsNavigation() }
    factory { BuySellNavigation() }
    factory { VerifyPurchaseNavigation() }
    factory { ConfirmationNavigation() }
    factory { OrderPreviewNavigation() }
    factory { SignUpNavigation() }
    factory { SingUpBusinessNavigation() }
    factory { SingUpChooseWalletNavigation() }
    factory { KYCProcessNavigation() }
    factory { KycNavigation() }
    factory { KYCManuallyNavigation() }
    factory { KYCWebViewNavigation() }
    factory { ForgotPassNavigator() }
    factory { SplashNavigation() }
    factory { ExchangeNavigator() }
    factory { PinNavigation() }

    single { RateTimer() }
}