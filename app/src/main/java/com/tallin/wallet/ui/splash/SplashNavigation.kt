package com.tallin.wallet.ui.splash

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator



class SplashNavigation : BaseNavigator() {

    fun goToLoginScreen(navController: NavController?) {
        try {
            navController?.navigate(R.id.action_splashFragment_to_loginFragment)
        } catch (e: java.lang.Exception){}

    }

    fun goToMainScreen(navController: NavController?) {
        try {
            navController?.navigate(R.id.action_splashFragment_to_pinFragment)
        }catch (e:Exception){}
    }

}