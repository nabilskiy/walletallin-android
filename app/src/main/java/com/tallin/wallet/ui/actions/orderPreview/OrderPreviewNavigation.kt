package com.tallin.wallet.ui.actions.orderPreview

import android.os.Bundle
import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class OrderPreviewNavigation : BaseNavigator() {

    fun goToConfirmationFragment(navController: NavController?, result: Boolean){
        val bundle = Bundle()
        bundle.putBoolean("[Buy-Sell]result", result)

        navController?.navigate(R.id.action_OrderPreviewFragment_to_ConfirmationFragment)
    }
}