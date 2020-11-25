package com.coinsliberty.wallet.ui.transaction

import androidx.navigation.NavController
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseNavigator

class TransactionNavigation : BaseNavigator() {
    fun goToDiagram(navController: NavController?) {
        navController?.navigate(R.id.action_transactionFragment_to_diagramFragment)
    }
}

