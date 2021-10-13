package com.tallin.wallet.ui.processKYC

import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class KYCProcessFragment() : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_kyc_process
    override val viewModel: KYCProcessViewModel by viewModel()
    override val navigator: KYCProcessNavigation = get()

}