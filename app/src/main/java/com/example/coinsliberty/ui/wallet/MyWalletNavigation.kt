package com.example.coinsliberty.ui.wallet

import android.os.Bundle
import androidx.navigation.NavController
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseNavigator

class MyWalletNavigation : BaseNavigator() {

    fun goToTransactions(navController: NavController?, bundle: Bundle) {
        navController?.navigate(R.id.action_myWalletFragment2_to_transactionFragment, bundle)
    }
}