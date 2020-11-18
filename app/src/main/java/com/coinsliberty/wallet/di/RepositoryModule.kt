package com.coinsliberty.wallet.di

import com.coinsliberty.wallet.dialogs.faceIdDialog.FaceIdRepository
import com.coinsliberty.wallet.dialogs.sendDialog.BtcRepository
import com.coinsliberty.wallet.dialogs.qrCode.QrCodeRepository
import com.coinsliberty.wallet.dialogs.ressPassword.ResetPassRepository
import com.coinsliberty.wallet.dialogs.forgetPassword.ForgotPassRepository
import com.coinsliberty.wallet.dialogs.secureCode.SecureCodeRepository
import com.coinsliberty.wallet.dialogs.touchIdDialog.TouchIdRepository
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.ui.pin.PinRepository
import com.coinsliberty.wallet.ui.profile.ProfileRepository
import com.coinsliberty.wallet.ui.settings.SettingsRepository
import com.coinsliberty.wallet.ui.signup.SignUpRepository
import com.coinsliberty.wallet.ui.wallet.WalletRepository
import org.koin.dsl.module


val repositoryModule = module {
    factory { SignUpRepository(get()) }
    factory { ForgotPassRepository(get()) }
    factory { WalletRepository(get()) }
    factory { LoginRepository(get()) }
    factory { ResetPassRepository(get()) }
    factory { ProfileRepository(get()) }
    factory { SettingsRepository(get()) }
    factory { QrCodeRepository(get()) }
    factory { SecureCodeRepository(get()) }
    factory { BtcRepository(get()) }
    factory { TouchIdRepository(get()) }
    factory { FaceIdRepository(get()) }
    factory { PinRepository(get()) }
}