package com.tallin.wallet.ui.fragmentActions

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_actions.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class ActionsFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_actions
    override val viewModel: ActionsViewModel by viewModel()
    override val navigator: ActionsNavigation = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.getUser()?.wallet?.kycProgramStatus == 1){
            btnBuySell.visible()
        } else {
            btnBuySell.gone()
        }

        btnBuySell.setOnClickListener {
            navigator.goToBuyFragment(navController)
        }
    }
}