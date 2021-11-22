package com.tallin.wallet.ui.kyc.manuallyKYC

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class KYCManuallyNavigation : BaseNavigator() {
    fun exitToSetting(navController: NavController?){
        navController?.navigate(R.id.action_exit_ManuallyKYC_to_SettingFragment)
    }
}
