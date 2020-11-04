package com.example.coinsliberty.ui.splash

import androidx.navigation.NavController
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseNavigator



class SplashNavigation : BaseNavigator() {

    fun goToLoginScreen(navController: NavController?) {
        navController?.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    fun goToMainScreen(navController: NavController?) {
        navController?.navigate(R.id.action_splashFragment_to_bottomTabNavigator)
    }

}