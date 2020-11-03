package com.example.coinsliberty.ui.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.model.SharedPreferencesProvider
import com.example.coinsliberty.ui.config.NavigationConfig


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