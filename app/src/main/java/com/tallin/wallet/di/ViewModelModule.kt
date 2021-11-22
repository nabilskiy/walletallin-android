package com.tallin.wallet.di

import com.tallin.wallet.dialogs.faceIdDialog.FaceIdViewModel
import com.tallin.wallet.dialogs.qrCode.QrCodeViewModel
import com.tallin.wallet.dialogs.ressPassword.ResetPassViewModel
import com.tallin.wallet.dialogs.sendDialog.SendBtcViewModel
import com.tallin.wallet.dialogs.forgetPassword.ForgotPassViewModel
import com.tallin.wallet.dialogs.makeTransaction.MakeTransactionViewModel
import com.tallin.wallet.dialogs.secureCode.SecureCodeViewModel
import com.tallin.wallet.dialogs.touchIdDialog.TouchIdViewModel
import com.tallin.wallet.ui.dialogs.ChangeLanguageViewModel
import com.tallin.wallet.ui.exchange.ExchangeViewModel
import com.tallin.wallet.ui.kyc.kyc.KycViewModel
import com.tallin.wallet.ui.kyc.manuallyKYC.KYCManuallyViewModel
import com.tallin.wallet.ui.login.LoginViewModel
import com.tallin.wallet.ui.pin.PinViewModel
import com.tallin.wallet.ui.kyc.processKYC.KYCProcessViewModel
import com.tallin.wallet.ui.kyc.webView.KYCWebViewViewModel
import com.tallin.wallet.ui.profile.ProfileViewModel
import com.tallin.wallet.ui.settings.SettingsViewModel
import com.tallin.wallet.ui.singup.chooseWallet.SingUpChooseWalletViewModel
import com.tallin.wallet.ui.singup.signup.SignUpViewModel
import com.tallin.wallet.ui.singup.singupBusiness.SignUpBusinessViewModel
import com.tallin.wallet.utils.stub.StubViewModel
import com.tallin.wallet.ui.splash.SplashViewModel
import com.tallin.wallet.ui.transaction.TransactionViewModel
import com.tallin.wallet.ui.wallet.MyWalletViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StubViewModel(get(), get(), get()) }

    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get(), get(), get()) }
    viewModel { SignUpBusinessViewModel(get(), get(), get(), get()) }
    viewModel { SingUpChooseWalletViewModel(get(), get(), get(), get()) }
    viewModel { KYCProcessViewModel(get(), get(), get(), get()) }
    viewModel { KycViewModel(get(), get(), get(), get()) }
    viewModel { KYCManuallyViewModel(get(), get(), get(), get()) }
    viewModel { KYCWebViewViewModel(get(), get(), get(), get()) }
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