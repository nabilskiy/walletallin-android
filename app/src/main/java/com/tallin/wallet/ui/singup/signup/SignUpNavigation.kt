package com.tallin.wallet.ui.singup.signup

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator


class SignUpNavigation : BaseNavigator() {

    fun goToLoginFragment(navController: NavController?) {
        navController?.navigate(R.id.action_signUpFragment_to_loginFragment)
    }
//    fun goToProfile(navController: NavController?) {
//        navController?.navigate(R.id.action_signUpFragmnet_to_profileFragment)
//    }

}