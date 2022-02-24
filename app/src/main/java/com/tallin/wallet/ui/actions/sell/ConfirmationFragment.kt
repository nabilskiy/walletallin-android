package com.tallin.wallet.ui.actions.sell

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_confirmation.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class ConfirmationFragment: BaseKotlinFragment() {

    override val layoutRes = R.layout.fragment_confirmation
    override val viewModel: ConfirmationViewModel by viewModel()
    override val navigator: ConfirmationNavigation = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getBoolean("[Buy-Sell]result")

        if (result == true) {
            tvTitleMsg.text = getString(R.string.waiting_for_your_coins)
            tvMsg.text = getString(R.string.your_transaction_is_in_progress)
            ivBack.gone()
        } else {
            tvTitleMsg.text = getString(R.string.transaction_failed)
            tvMsg.text = getString(R.string.please_go_back_for_another_try)
            ivBack.visible()
        }
        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}