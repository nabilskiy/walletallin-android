package com.example.coinsliberty.di

import com.example.coinsliberty.ui.dialogs.ChangeLanguageViewModel
import com.example.coinsliberty.ui.exchange.ExchangeViewModel
import com.example.coinsliberty.ui.login.LoginViewModel
import com.example.coinsliberty.ui.profile.ProfileViewModel
import com.example.coinsliberty.ui.settings.SettingsViewModel
import com.example.coinsliberty.ui.signup.SignUpViewModel
import com.example.coinsliberty.utils.stub.StubViewModel
import com.example.coinsliberty.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StubViewModel(get()) }

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { ExchangeViewModel(get()) }
    viewModel { ChangeLanguageViewModel(get()) }
}