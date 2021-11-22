package com.tallin.wallet.ui.kyc.webView

import android.app.Application
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository

class KYCWebViewViewModel(
    app: Application,
    private val repository: KYCWebViewRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {
    
    override fun stopRequest() {

    }

}