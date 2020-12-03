package com.coinsliberty.wallet.ui.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.config.NavigationConfig
import com.coinsliberty.wallet.ui.login.LoginRepository


class SplashViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val ldNavigate = MutableLiveData<NavigationConfig>()

    fun navigate() {
       if(sharedPreferencesProvider.getToken().isNullOrEmpty()) {
           ldNavigate.postValue(NavigationConfig.GO_TO_LOGIN)
       } else {
           ldNavigate.postValue(NavigationConfig.GO_TO_MAIN)
       }
    }

}