package com.tallin.wallet.ui.singup.singupBusiness

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class SingUpBusinessNavigation : BaseNavigator() {

    fun goToLoginFragment(navController: NavController?) {
        navController?.navigate(R.id.action_signUpBusinessFragment_to_loginFragment)
    }

}