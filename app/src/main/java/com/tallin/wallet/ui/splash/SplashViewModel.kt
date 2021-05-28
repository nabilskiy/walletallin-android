package com.tallin.wallet.ui.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.config.NavigationConfig
import com.tallin.wallet.ui.login.LoginRepository


class SplashViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val ldNavigate = MutableLiveData<NavigationConfig>()

    override fun stopRequest() {

    }

    fun navigate() {
       if(sharedPreferencesProvider.getToken().isNullOrEmpty()) {
           ldNavigate.postValue(NavigationConfig.GO_TO_LOGIN)
       } else {
           ldNavigate.postValue(NavigationConfig.GO_TO_MAIN)
       }
    }

}