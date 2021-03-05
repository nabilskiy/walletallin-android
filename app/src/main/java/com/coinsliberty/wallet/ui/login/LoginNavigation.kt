package com.coinsliberty.wallet.ui.login

import androidx.navigation.NavController
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseNavigator

class LoginNavigation : BaseNavigator() {

    fun goToSignUp(navController: NavController?) {
        navController?.navigate(R.id.action_loginFragment_to_signUpFragmnet)
    }
    fun goToContent(navController: NavController?) {
         navController?.navigate(R.id.action_loginFragment_to_pinFragment)
    }
    fun goToForgotPassword(navController: NavController?){
        navController?.navigate(R.id.action_loginFragment_to_forgot_pass_fragment)
    }
}