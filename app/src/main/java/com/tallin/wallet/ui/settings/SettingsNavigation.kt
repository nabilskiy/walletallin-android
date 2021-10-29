package com.tallin.wallet.ui.settings

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator



class SettingsNavigation : BaseNavigator() {

    fun goToKyc(navController: NavController?){
        navController?.navigate(R.id.action_settingsFragment_to_processKYC)
    }
//    fun goToProfile(navController: NavController?) {
//        navController?.navigate(R.id.action_settingsFragment_to_profileFragment2)
//    }

}