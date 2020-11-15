package com.coinsliberty.wallet.ui.wallet

import android.os.Bundle
import androidx.navigation.NavController
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseNavigator

class MyWalletNavigation : BaseNavigator() {

    fun goToTransactions(navController: NavController?, bundle: Bundle) {
        navController?.navigate(R.id.action_myWalletFragment2_to_transactionFragment, bundle)
    }
}