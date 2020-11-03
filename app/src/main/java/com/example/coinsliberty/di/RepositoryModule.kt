package com.example.coinsliberty.di

import com.example.coinsliberty.dialogs.forgetPassword.ForgotPassRepository
import com.example.coinsliberty.ui.login.LoginRepository
import com.example.coinsliberty.ui.signup.SignUpRepository
import org.koin.dsl.module


val repositoryModule = module {
    factory { SignUpRepository(get()) }
    factory { ForgotPassRepository(get()) }
    factory { LoginRepository(get()) }
}