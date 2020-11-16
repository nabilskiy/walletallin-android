package com.coinsliberty.wallet.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseAdapter
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.data.response.BalanceInfoResponse
import com.coinsliberty.wallet.data.response.TransactionItem
import com.coinsliberty.wallet.dialogs.AcceptDialog
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.dialogs.sendDialog.SendDialog
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionDataHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionTitleHolder
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.coinsliberty.wallet.utils.extensions.gone
import com.coinsliberty.wallet.utils.extensions.visible
import com.coinsliberty.wallet.utils.isDifferrentDate
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val keyBundleBalance = "balance"
private const val keyBundleWallet = "wallet"
private const val keyBundleRates = "rates"

class TransactionFragment : BaseKotlinFragment() {

    override val layoutRes = R.layout.fragment_transaction
    override val viewModel: TransactionViewModel by viewModel()
    override val navigator: TransactionNavigation = TransactionNavigation()

    private var sendDialog: SendDialog? = null

    private var rates: Double = 0.0
    private var balanceData: Double = 0.0
    private var wallet: Int? = null

    val adapter = BaseAdapter()
        .map(R.layout.item_transaction, TransactionHolder())
        .map(R.layout.item_data, TransactionDataHolder())
        .map(R.layout.item_title, TransactionTitleHolder())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rates = arguments?.getDouble(keyBundleRates) ?: 0.0
        balanceData = arguments?.getDouble(keyBundleBalance) ?: 0.0
        wallet = arguments?.getInt(keyBundleWallet)

        ivTransactionLeftIcon.setOnClickListener {
            activity?.onBackPressed()
        }


        btnMakeTransfer.setOnClickListener {
            if(sendDialog == null) {
                sendDialog = SendDialog.newInstance("Sent btc", rates ?: 0.0, balanceData ?: 0.0)
            }
            sendDialog
                ?.apply {
                    initListeners { result, text ->
                        showResult(result, text)
                    }
                }
                ?.show(childFragmentManager, SendDialog.TAG)
        }
//        transactionRecieveButton.setOnClickListener {
//            QrCodeDialog.newInstance("Sent btc", "test").show(childFragmentManager, QrCodeDialog.TAG)
//        }
        subscribeLiveData()

        viewModel.getTransaction()

        rvTransactions.adapter = adapter
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.transactionsLiveData, ::initTransactions)
        bindDataTo(viewModel.balanceLiveData, ::initBalance)
    }

    private fun initTransactions(list: List<TransactionItem>?) {
        if (list.isNullOrEmpty()) {
            ivNoTransaction.visible()
            tvNoTransaction.visible()
            return
        }
        ivNoTransaction.gone()
        tvNoTransaction.gone()

        adapter.itemsAdded(listOf("Transactions"))
        adapter.itemsAdded(getTransactions(list))
    }

    private fun initBalance(balance: BalanceInfoResponse) {
        tvBalanceCrypto.text = String.format("%.8f", balance.availableBalances?.btc)
        tvBalanceFiat.text = String.format("%.2f",balance.availableBalances?.btc?.times(rates))

        tvTotalBalanceCrypto.text = String.format("%.8f", balance.balances?.btc)
        tvTotalBalanceFiat.text = String.format("%.2f",balance.balances?.btc?.times(rates))
    }

    private fun getTransactions(list: List<TransactionItem>?): List<Any>? {
        if (list.isNullOrEmpty()) return emptyList()
        val resultList = ArrayList<Any>()
        var data: Long? = null
        list.forEach {
            if (data == null || isDifferrentDate(data ?: 0, it.time ?: 0)) {
                resultList.add(it.time ?: 0)
                data = it.time
            }
            resultList.add(it.apply {
                it.amountUsd = String.format("%.2f", (it.amount?.toDouble() ?: 0.0) * (rates))
            })
        }
        return resultList
    }

    private fun showResult(it: Boolean, balance: String? = null) {
        if (it) {
            sendDialog?.dismiss()
            AcceptDialog.newInstance(balance ?: "", "Success")
                .show(childFragmentManager, AcceptDialog.TAG)
        } else {
            ErrorDialog.newInstance("Empty field").show(childFragmentManager, ErrorDialog.TAG)
        }
    }

    companion object {
        val TAG: String = com.coinsliberty.wallet.ui.transaction.TransactionFragment::class.java.name
        fun getBundle(rates: Double?, balance: Double?, wallet: Int?): Bundle {
            val bundle = bundleOf(
                keyBundleRates to rates,
                keyBundleBalance to balance,
                keyBundleWallet to wallet
            )
            return bundle
        }
    }
}
