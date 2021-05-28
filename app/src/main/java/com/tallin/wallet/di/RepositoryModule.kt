package com.tallin.wallet.di

import com.tallin.wallet.dialogs.faceIdDialog.FaceIdRepository
import com.tallin.wallet.dialogs.sendDialog.BtcRepository
import com.tallin.wallet.dialogs.qrCode.QrCodeRepository
import com.tallin.wallet.dialogs.ressPassword.ResetPassRepository
import com.tallin.wallet.dialogs.forgetPassword.ForgotPassRepository
import com.tallin.wallet.dialogs.makeTransaction.MakeTransactionRepository
import com.tallin.wallet.dialogs.secureCode.SecureCodeRepository
import com.tallin.wallet.dialogs.touchIdDialog.TouchIdRepository
import com.tallin.wallet.ui.exchange.ExchangeRepository
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.ui.pin.PinRepository
import com.tallin.wallet.ui.profile.ProfileRepository
import com.tallin.wallet.ui.settings.SettingsRepository
import com.tallin.wallet.ui.signup.SignUpRepository
import com.tallin.wallet.ui.wallet.WalletRepository
import com.tallin.wallet.utils.manager.BiometricManager
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
    factory { ExchangeRepository(get()) }
    factory { MakeTransactionRepository(get(),get(),get()) }
    factory { TouchIdRepository(get()) }
    factory { FaceIdRepository(get()) }
    factory { PinRepository(get()) }

    factory { BiometricManager(get(), get()) }
}