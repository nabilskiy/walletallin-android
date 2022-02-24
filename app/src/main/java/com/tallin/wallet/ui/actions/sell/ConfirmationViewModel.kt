package com.tallin.wallet.ui.actions.sell

import android.app.Application
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository

class ConfirmationViewModel (
    app: Application,
    private val repository: ConfirmationRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {


    override fun stopRequest() {

    }
}