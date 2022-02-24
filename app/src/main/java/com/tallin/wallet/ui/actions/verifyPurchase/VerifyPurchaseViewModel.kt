package com.tallin.wallet.ui.actions.verifyPurchase

import android.app.Application
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository

class VerifyPurchaseViewModel (
    app: Application,
    private val repository: VerifyPurchaseRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {


    override fun stopRequest() {

    }
}