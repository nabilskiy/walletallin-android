package com.coinsliberty.wallet.utils.stub

import android.app.Application
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository

class StubViewModel(
    private val app: Application,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    override fun stopRequest() {

    }

}