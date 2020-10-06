package com.example.moneybee.ui.splash

import androidx.navigation.NavController
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseNavigator



class SplashNavigation : BaseNavigator() {

//    fun goToSignUp(navController: NavController?) {
//        navController?.navigate(R.id.action_splashFragment_to_signUpFragmnet)
//    }

    fun goToLoginScreen(navController: NavController?) {
        navController?.navigate(R.id.action_splashFragment_to_loginFragment)
    }
}