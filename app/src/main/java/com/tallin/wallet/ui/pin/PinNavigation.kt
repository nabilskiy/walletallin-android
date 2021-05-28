package com.tallin.wallet.ui.pin

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class PinNavigation : BaseNavigator() {

    fun goToMain(navController: NavController?) {
        navController?.navigate(R.id.action_pinFragment_to_bottomTabNavigator)
    }

}