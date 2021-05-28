package com.tallin.wallet.ui.wallet

import android.os.Bundle
import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class MyWalletNavigation : BaseNavigator() {

    fun goToTransactions(navController: NavController?, bundle: Bundle) {
        navController?.navigate(R.id.action_myWalletFragment2_to_transactionFragment, bundle)
    }
}