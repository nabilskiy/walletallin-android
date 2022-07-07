package com.tallin.wallet.ui.fragmentActions

import android.app.Application
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository

class ActionsViewModel(
    app: Application,
    private val repository: ActionsRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    fun getUser() = sharedPreferencesProvider.getUser()

    override fun stopRequest() {}

    fun getKycStatus() : Boolean {
        return if (sharedPreferencesProvider.getUser()?.wallet?.kycProgramStatus != 1 && !sharedPreferencesProvider.getKycStatus()){
            sharedPreferencesProvider.saveKycStatus(true)
            false
        }else true
    }
}