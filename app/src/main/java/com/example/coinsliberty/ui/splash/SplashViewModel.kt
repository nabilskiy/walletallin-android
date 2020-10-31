package com.example.coinsliberty.ui.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.ui.config.NavigationConfig


class SplashViewModel(private val app: Application): BaseViewModel(app) {

    val ldNavigate = MutableLiveData<NavigationConfig>()

    fun navigate() {
       
    }

}