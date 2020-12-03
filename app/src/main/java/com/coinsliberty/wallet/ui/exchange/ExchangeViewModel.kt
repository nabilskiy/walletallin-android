package com.coinsliberty.wallet.ui.exchange

import android.app.Application
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository

class ExchangeViewModel(
    private val app: Application,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {


}