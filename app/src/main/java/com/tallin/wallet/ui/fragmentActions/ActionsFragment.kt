package com.tallin.wallet.ui.fragmentActions

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import kotlinx.android.synthetic.main.fragment_actions.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class ActionsFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_actions
    override val viewModel: ActionsViewModel by viewModel()
    override val navigator: ActionsNavigation = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBuySell.setOnClickListener {
            navigator.goToBuyFragment(navController)
        }
        /*btnSell.setOnClickListener {
            navigator.goToSellFragment(navController)
        }*/
    }
}