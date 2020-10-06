package com.example.coinsliberty.di

import com.example.coinsliberty.ui.dialogs.ChangeLanguageViewModel
import com.example.coinsliberty.ui.login.LoginViewModel
import com.example.coinsliberty.ui.signup.SignUpViewModel
import com.example.coinsliberty.utils.stub.StubViewModel
import com.example.moneybee.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StubViewModel() }

    viewModel { SplashViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { ChangeLanguageViewModel() }
}