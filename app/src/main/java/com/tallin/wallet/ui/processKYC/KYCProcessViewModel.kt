package com.tallin.wallet.ui.processKYC

import android.app.Application
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository

class KYCProcessViewModel(
    app: Application,
    private val repository: KYCProcessRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    override fun stopRequest() {
    }
}