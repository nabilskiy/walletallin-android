package com.example.coinsliberty.ui.profile

import androidx.navigation.NavController
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseNavigator

class ProfileNavigation: BaseNavigator() {

    fun goToSignUp(navController: NavController?) {
        navController?.navigate(R.id.action_loginFragment_to_signUpFragmnet)
    }

}