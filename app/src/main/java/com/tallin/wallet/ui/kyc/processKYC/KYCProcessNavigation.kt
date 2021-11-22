package com.tallin.wallet.ui.kyc.processKYC

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class KYCProcessNavigation : BaseNavigator() {
    fun goToKyc(navController: NavController?){
        navController?.navigate(R.id.action_ProcessKYC_to_KYC)
    }
}