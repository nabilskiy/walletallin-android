package com.coinsliberty.wallet.di

import com.coinsliberty.wallet.dialogs.faceIdDialog.FaceIdViewModel
import com.coinsliberty.wallet.dialogs.qrCode.QrCodeViewModel
import com.coinsliberty.wallet.dialogs.ressPassword.ResetPassViewModel
import com.coinsliberty.wallet.dialogs.sendDialog.SendBtcViewModel
import com.coinsliberty.wallet.dialogs.forgetPassword.ForgotPassViewModel
import com.coinsliberty.wallet.dialogs.makeTransaction.MakeTransactionViewModel
import com.coinsliberty.wallet.dialogs.secureCode.SecureCodeViewModel
import com.coinsliberty.wallet.dialogs.touchIdDialog.TouchIdViewModel
import com.coinsliberty.wallet.ui.dialogs.ChangeLanguageViewModel
import com.coinsliberty.wallet.ui.exchange.ExchangeViewModel
import com.coinsliberty.wallet.ui.login.LoginViewModel
import com.coinsliberty.wallet.ui.pin.PinViewModel
import com.coinsliberty.wallet.ui.profile.ProfileViewModel
import com.coinsliberty.wallet.ui.settings.SettingsViewModel
import com.coinsliberty.wallet.ui.signup.SignUpViewModel
import com.coinsliberty.wallet.utils.stub.StubViewModel
import com.coinsliberty.wallet.ui.splash.SplashViewModel
import com.coinsliberty.wallet.ui.transaction.TransactionViewModel
import com.coinsliberty.wallet.ui.wallet.MyWalletViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StubViewModel(get(), get(), get()) }

    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get()) }
    viewModel { ExchangeViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { ChangeLanguageViewModel(get(), get(), get()) }
    viewModel { ForgotPassViewModel(get(), get(), get(), get()) }
    viewModel { MyWalletViewModel(get(), get(), get(), get()) }
    viewModel { TransactionViewModel(get(), get(), get(), get()) }
    viewModel { ResetPassViewModel(get(), get(), get(), get()) }
    viewModel { QrCodeViewModel(get(), get(), get(), get()) }
    viewModel { SecureCodeViewModel(get(), get(), get(), get()) }
    viewModel { SendBtcViewModel(get(), get(), get(), get()) }
    viewModel { MakeTransactionViewModel(get(), get(), get(), get()) }
    viewModel { TouchIdViewModel(get(), get(), get(), get()) }
    viewModel { FaceIdViewModel(get(), get(), get(), get()) }
    viewModel { PinViewModel(get(), get(), get(), get(), get()) }
}