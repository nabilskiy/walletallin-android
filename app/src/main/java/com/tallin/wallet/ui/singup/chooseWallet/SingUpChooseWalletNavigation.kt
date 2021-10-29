package com.tallin.wallet.ui.singup.chooseWallet

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class SingUpChooseWalletNavigation : BaseNavigator() {

    fun goToBusinessSingUp(navController: NavController?) {
        navController?.navigate(R.id.action_chooseFragment_to_signUpBusinessFragmnet)
    }

    fun goToPrivateSingUp(navController: NavController?) {
        navController?.navigate(R.id.action_chooseFragment_to_signUpFragmnet)
    }

}