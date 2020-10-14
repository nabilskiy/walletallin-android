package com.example.coinsliberty.ui.transaction

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment


class TransactionFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_transaction
    override val viewModel: TransactionViewModel = TransactionViewModel()
    override val navigator: TransactionNavigation = TransactionNavigation()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun navigate() {}

}
