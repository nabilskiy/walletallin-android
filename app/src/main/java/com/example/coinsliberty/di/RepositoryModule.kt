package com.example.coinsliberty.di

import com.example.coinsliberty.dialogs.ResetPassRepository
import com.example.coinsliberty.dialogs.forgetPassword.ForgotPassRepository
import com.example.coinsliberty.ui.login.LoginRepository
import com.example.coinsliberty.dialogs.ForgotPassRepository
import com.example.coinsliberty.ui.profile.ProfileRepository
import com.example.coinsliberty.ui.signup.SignUpRepository
import com.example.coinsliberty.ui.wallet.WalletRepository
import org.koin.dsl.module


val repositoryModule = module {
    factory { SignUpRepository(get()) }
    factory { ForgotPassRepository(get()) }
    factory { WalletRepository(get()) }
    factory { LoginRepository(get()) }
    factory { ResetPassRepository(get()) }
    factory { ProfileRepository(get()) }
}