package com.example.coinsliberty.ui.login

import android.os.Bundle
import androidx.navigation.NavController
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseNavigator

class LoginNavigation : BaseNavigator() {

    fun goToSignUp(navController: NavController?) {
        navController?.navigate(R.id.action_loginFragment_to_signUpFragmnet)
    }
    fun goToContent(navController: NavController?) {
         navController?.navigate(R.id.action_loginFragment_to_bottomTabNavigator)
    }
}