package com.tallin.wallet.ui.exchange

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class ExchangeNavigator : BaseNavigator() {

    fun goToKYCProcess(navController: NavController?) {
        navController?.navigate(R.id.action_exchangeFragment_to_processKYC)
    }
}