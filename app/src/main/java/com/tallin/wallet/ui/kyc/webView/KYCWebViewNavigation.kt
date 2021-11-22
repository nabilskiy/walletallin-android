package com.tallin.wallet.ui.kyc.webView

import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator

class KYCWebViewNavigation : BaseNavigator() {
    fun exitToSetting(navController: NavController?){
        navController?.navigate(R.id.action_exit_WebViewKYC_to_SettingFragment)
    }

}