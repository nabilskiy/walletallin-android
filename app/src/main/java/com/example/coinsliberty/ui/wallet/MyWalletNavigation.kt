package com.example.coinsliberty.ui.wallet

import androidx.navigation.NavController
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseNavigator

class MyWalletNavigation : BaseNavigator() {

    fun goToTransactions(navController: NavController?) {
        navController?.navigate(R.id.action_myWalletFragment2_to_transactionFragment)
    }
}