package com.example.coinsliberty.ui.transaction

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.dialogs.QrCodeDialog
import com.example.coinsliberty.dialogs.SendDialog
import kotlinx.android.synthetic.main.fragment_my_wallet.*
import kotlinx.android.synthetic.main.fragment_transaction.*


class TransactionFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_transaction
    override val viewModel: TransactionViewModel = TransactionViewModel()
    override val navigator: TransactionNavigation = TransactionNavigation()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionSendButton.setOnClickListener {
            SendDialog.newInstance("Sent eth", "test", "100", "100", "test", "test").show(childFragmentManager, SendDialog.TAG)
        }
        transactionRecieveButton.setOnClickListener {
            QrCodeDialog.newInstance("Sent eth", "test").show(childFragmentManager, QrCodeDialog.TAG)
        }
    }
}
