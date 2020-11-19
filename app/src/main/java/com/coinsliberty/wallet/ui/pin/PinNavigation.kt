package com.coinsliberty.wallet.ui.pin

import androidx.navigation.NavController
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseNavigator

class PinNavigation : BaseNavigator() {

    fun goToMain(navController: NavController?) {
        navController?.navigate(R.id.action_pinFragment_to_bottomTabNavigator)
    }

}