package com.tallin.wallet.ui.actions.verifyPurchase

import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class VerifyPurchaseFragment: BaseKotlinFragment() {

    override val layoutRes = R.layout.fragment_buy_sell
    override val viewModel: VerifyPurchaseViewModel by viewModel()
    override val navigator: VerifyPurchaseNavigation = get()
}