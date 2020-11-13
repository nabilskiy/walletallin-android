package com.example.coinsliberty.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.data.response.TransactionItem
import com.example.coinsliberty.dialogs.*
import com.example.coinsliberty.dialogs.qrCode.QrCodeDialog
import com.example.coinsliberty.dialogs.sendDialog.SendDialog
import com.example.coinsliberty.ui.wallet.adapters.TransactionDataHolder
import com.example.coinsliberty.ui.wallet.adapters.TransactionHolder
import com.example.coinsliberty.ui.wallet.adapters.TransactionTitleHolder
import com.example.coinsliberty.utils.extensions.bindDataTo
import com.example.coinsliberty.utils.extensions.gone
import com.example.coinsliberty.utils.extensions.visible
import com.example.coinsliberty.utils.isDifferrentDate
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.koin.android.viewmodel.ext.android.viewModel

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

        //ivTransactionLogo.setImageResource(wallet ?: 0)
        //tvTransactionTitle.text = "$balanceData BTC"
        //tvTransactionTotalBalance.text = "= " + (balanceData * rates).toString() + " $"

        transactionSendButton.setOnClickListener {
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
        transactionRecieveButton.setOnClickListener {
            QrCodeDialog.newInstance("Sent btc", "test").show(childFragmentManager, QrCodeDialog.TAG)
        }
        subscribeLiveData()

        viewModel.getTransaction()

        rvTransactions.adapter = adapter
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.transactionsLiveData, ::initTransactions)
        bindDataTo(viewModel.availableBalanceLiveData, :: initAvailableBalance)
    }

    private fun initTransactions(list: List<TransactionItem>?) {
        if(list.isNullOrEmpty()) {
            ivNoTransaction.visible()
            tvNoTransaction.visible()
            return
        }
        ivNoTransaction.gone()
        tvNoTransaction.gone()

        adapter.itemsAdded(listOf("Transactions"))
        adapter.itemsAdded(getTransactions(list))
    }

    private fun initAvailableBalance(balance : Double){
        tvBalanceCrypto.text = String.format("%.8f",balance)
        tvBalanceFiat.text = (balance * rates).toString()
    }

    private fun getTransactions(list: List<TransactionItem>?): List<Any>? {
        if(list.isNullOrEmpty()) return emptyList()
        val resultList = ArrayList<Any>()
        var data: Long? = null
        list.forEach {
            if (data == null || isDifferrentDate(data ?: 0, it.time ?: 0)) {
                resultList.add(it.time ?: 0)
                data = it.time
            }
            resultList.add(it.apply { it.amountUsd = String.format("%.2f",(it.amount?.toDouble() ?: 0.0) * (rates)) })
        }
        return resultList
    }

    private fun showResult(it: Boolean, balance: String? = null) {
        if(it) {
            sendDialog?.dismiss()
            AcceptDialog.newInstance(balance ?: "", "Success").show(childFragmentManager, AcceptDialog.TAG)
        } else {
            ErrorDialog.newInstance("Empty field").show(childFragmentManager, ErrorDialog.TAG)
        }
    }

    companion object {
        val TAG: String = TransactionFragment::class.java.name
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
