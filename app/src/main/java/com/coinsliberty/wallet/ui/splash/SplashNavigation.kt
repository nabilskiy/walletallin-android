package com.coinsliberty.wallet.ui.splash

import androidx.navigation.NavController
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseNavigator



class SplashNavigation : BaseNavigator() {

    fun goToLoginScreen(navController: NavController?) {
        navController?.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    fun goToMainScreen(navController: NavController?) {
        navController?.navigate(R.id.action_splashFragment_to_pinFragment)
    }

}