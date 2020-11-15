package com.coinsliberty.wallet.ui.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.config.NavigationConfig


class SplashViewModel(private val app: Application,private val sharedPreferencesProvider: SharedPreferencesProvider): BaseViewModel(app) {

    val ldNavigate = MutableLiveData<NavigationConfig>()

    fun navigate() {
        Log.e("!!!", "==empty")
        Log.e("!!!", sharedPreferencesProvider.getToken() ?: "empty")
       if(sharedPreferencesProvider.getToken().isNullOrEmpty()) {
           ldNavigate.postValue(NavigationConfig.GO_TO_LOGIN)
       } else {
           ldNavigate.postValue(NavigationConfig.GO_TO_MAIN)
       }
    }

}