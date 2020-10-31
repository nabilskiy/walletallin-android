package com.example.coinsliberty.ui.transaction

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.dialogs.AcceptDialog
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.dialogs.QrCodeDialog
import com.example.coinsliberty.dialogs.SendDialog
import com.example.coinsliberty.utils.extensions.gone
import com.example.coinsliberty.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_my_wallet.*
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.android.synthetic.main.item_transaction.*
import kotlinx.android.synthetic.main.item_transaction.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class TransactionFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_transaction
    override val viewModel: TransactionViewModel by viewModel()
    override val navigator: TransactionNavigation = TransactionNavigation()

    private val sendDialog = SendDialog
        .newInstance("Sent eth", "test", "100", "100", "USD", "EUR")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionSendButton.setOnClickListener {
            sendDialog
                .apply {
                    initListeners {
                        showResult(it)
                    }
                }
                .show(childFragmentManager, SendDialog.TAG)
        }
        transactionRecieveButton.setOnClickListener {
            QrCodeDialog.newInstance("Sent eth", "test").show(childFragmentManager, QrCodeDialog.TAG)
        }
        transactions.tvTitle.text = "Transaction"

        first.setOnClickListener {
            it.gone()
            firstOpen.visible()
        }
        firstOpen.setOnClickListener {
            it.gone()
            first.visible()
        }

        second.setOnClickListener {
            it.gone()
            secondOpen.visible()
        }
        secondOpen.setOnClickListener {
            it.gone()
            second.visible()
        }

        third.setOnClickListener {
            it.gone()
            thirdOpen.visible()
        }
        thirdOpen.setOnClickListener {
            it.gone()
            third.visible()
        }

        fourth.setOnClickListener {
            it.gone()
            fourthOpen.visible()
        }
        fourthOpen.setOnClickListener {
            it.gone()
            fourth.visible()
        }
    }

    private fun showResult(it: Boolean) {
        if(it) {
            sendDialog.dismiss()
            AcceptDialog.newInstance("1000.00", "Success").show(childFragmentManager, AcceptDialog.TAG)
        } else {
            ErrorDialog.newInstance("Empty field").show(childFragmentManager, ErrorDialog.TAG)
        }
    }
}
