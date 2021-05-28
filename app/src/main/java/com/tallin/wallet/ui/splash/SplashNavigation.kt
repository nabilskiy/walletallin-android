package com.tallin.wallet.ui.splash

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator



class SplashNavigation : BaseNavigator() {

    fun goToLoginScreen(navController: NavController?) {
        navController?.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    fun goToMainScreen(navController: NavController?) {
        navController?.navigate(R.id.action_splashFragment_to_pinFragment)
    }

}