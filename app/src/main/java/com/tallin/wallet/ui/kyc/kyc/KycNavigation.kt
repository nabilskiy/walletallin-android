package com.tallin.wallet.ui.kyc.kyc

import android.os.Bundle
import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator
import com.tallin.wallet.ui.MainActivity


class KycNavigation : BaseNavigator() {
    fun goToKycProcess(navController: NavController?, link: String, externalId: String){
        val bundle = Bundle()
        bundle.putString("[KYC]link", link)
        bundle.putString("[KYC]externalId", externalId)
        navController?.navigate(R.id.action_KYC_to_WebViewKYC, bundle)
    }
    fun goToKycManually(navController: NavController?, id: Int, docId: Int){
        val bundle = Bundle()
        bundle.putInt("[KYC]id", id)
        bundle.putInt("[KYC]docId", docId)
        navController?.navigate(R.id.action_KYC_to_ManuallyKYC, bundle)
    }
}