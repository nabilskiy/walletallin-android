package com.tallin.wallet.ui.login

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class LoginNavigation : BaseNavigator() {

    fun goToSignUp(navController: NavController?) {
        navController?.navigate(R.id.action_loginFragment_to_signUpFragmnet)
    }

    fun goToContent(navController: NavController?) {
        try {
            navController?.navigate(R.id.action_loginFragment_to_pinFragment)
        } catch (e: IllegalArgumentException) {

        }
    }

    fun goToForgot(navController: NavController?){
        navController?.navigate(R.id.action_loginFragment_to_forgot_pass_fragment)
    }
}