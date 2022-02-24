package com.tallin.wallet.ui.fragmentActions

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class ActionsNavigation : BaseNavigator() {
    fun goToBuyFragment(navController: NavController?){
        navController?.navigate(R.id.action_actionsFragment_to_BuyFragment)
    }
}