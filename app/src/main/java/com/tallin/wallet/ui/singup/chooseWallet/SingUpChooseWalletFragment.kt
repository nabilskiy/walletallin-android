package com.tallin.wallet.ui.singup.chooseWallet

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.WalletTypesResponse
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_choose_wallet.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class SingUpChooseWalletFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_choose_wallet
    override val viewModel: SingUpChooseWalletViewModel by viewModel()
    override val navigator: SingUpChooseWalletNavigation = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWalletTypes()

        ivBack.setOnClickListener { activity?.onBackPressed() }

        btnPrivateAccount.setOnClickListener {
            navigator.goToPrivateSingUp(navController)
        }

        btnBusinessAccount.setOnClickListener {
            navigator.goToBusinessSingUp(navController)
        }

        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::showResult)
    }

    private fun showResult(b: WalletTypesResponse) {
        if (b.result == true) {

        }
    }
}