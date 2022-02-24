package com.tallin.wallet.ui.actions.buy

import android.os.Bundle
import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class BuySellNavigation : BaseNavigator() {

    fun goToOrderPreviewFragment(
        navController: NavController?,
        fiatAmount: Double,
        fiatCurrency: String,
        cryptoAmount: Double,
        cryptoCurrency: String,
        rate: String,
        operation: Boolean
    ){
        val bundle = Bundle()
        bundle.putDouble("[Buy-Sell]fiatAmount", fiatAmount)
        bundle.putString("[Buy-Sell]fiatCurrency", fiatCurrency)
        bundle.putDouble("[Buy-Sell]cryptoAmount", cryptoAmount)
        bundle.putString("[Buy-Sell]cryptoCurrency", cryptoCurrency)
        bundle.putString("[Buy-Sell]rate", rate)
        bundle.putBoolean("[Buy-Sell]operation", operation)
        navController?.navigate(R.id.action_BuySellFragment_to_OrderPreviewFragment, bundle)
    }
}