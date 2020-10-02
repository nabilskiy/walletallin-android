package com.example.moneybee.ui.splash

import androidx.navigation.NavController
import com.example.coinsliberty.base.BaseNavigator


class SplashNavigation : BaseNavigator() {

    fun goToSetUp(navController: NavController?) {
       // navController?.navigate(R.id.action_splashFragment_to_setUpFragment)
    }

    fun goToDashboard(navController: NavController?) {
       // navController?.navigate(R.id.action_splashFragment_to_dashboardFragment)
    }

    fun goTouUserTerm(navController: NavController?) {
       // navController?.navigate(R.id.action_splashFragment_to_userTermFragment)
    }
}