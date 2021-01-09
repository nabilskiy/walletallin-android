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
import com.coinsliberty.wallet.dialogs.makeTransaction.MakeTransactionDialog
import com.coinsliberty.wallet.ui.MainActivity
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionDataHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionTitleHolder
import com.coinsliberty.wallet.utils.currency.Currency
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

    private var makeTransactionDialog: MakeTransactionDialog? = null

    private var rates: Double = 0.0
    private var currency: Currency = Currency.USD
    private var balanceData: Double = 0.0
    private var wallet: Int? = null

    val adapter = BaseAdapter()
        .map(R.layout.item_transaction, TransactionHolder())
        .map(R.layout.item_data, TransactionDataHolder())
        .map(R.layout.item_title, TransactionTitleHolder())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnMakeTransfer.setOnClickListener {

            if (makeTransactionDialog == null) {
                makeTransactionDialog =
                    MakeTransactionDialog.newInstance(
                        "BTC",
                        rates,
                        balanceData ?: 0.0,
                        null,
                        "17325782351905019asdofjkas",
                        "ss"
                    )
            }
            makeTransactionDialog?.apply {
                initListeners { result, text ->
                    showResult(result, text)
                }
            }
                ?.show(childFragmentManager, MakeTransactionDialog.TAG)
        }


        rates = arguments?.getDouble(keyBundleRates) ?: 0.0
        balanceData = arguments?.getDouble(keyBundleBalance) ?: 0.0
        wallet = arguments?.getInt(keyBundleWallet)

        ivTransactionLeftIcon.setOnClickListener {
            activity?.onBackPressed()
        }

        clBTCPriceForOne.setOnClickListener {
            navigator.goToDiagram(navController)
        }

        subscribeLiveData()

        rvTransactions.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        activity.let {
            (it as MainActivity).showPin = false
        }
        viewModel.getCurrency()
        viewModel.getTransaction()
    }

    override fun onPause() {
        super.onPause()
        val currentFragmentMenu = navController?.currentDestination?.label
        if (currentFragmentMenu != "TransactionFragment") {
            activity.let {
                (it as MainActivity).showPin = true
            }
        }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.transactionsLiveData, ::initTransactions)
        bindDataTo(viewModel.balanceLiveData, ::initBalance)
        bindDataTo(viewModel.currency, ::initCurrency)
    }

    private fun initCurrency(currency: Currency?) {
        if(currency == null) return
        this.currency = currency
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
        tvBTCPrice.text = String.format("%.2f", rates) + " ${currency.getTitle()}"
        tvTransactionTitle.text = String.format("%.8f", balance.balances?.btc) + " BTC"
        tvTransactionTotalBalance.text =  "= " + String.format("%.2f", balance.balances?.btc?.times(rates)) + if(currency == null || currency == Currency.USD)" $" else " €"
        viewModel.updateBalance()
    }

    private fun getTransactions(list: List<TransactionItem>?): List<Any>? {
        if(list.isNullOrEmpty()) return emptyList()

        val resultList = ArrayList<Any>()
        var lastItem: TransactionItem? = null
        list.forEachIndexed { _, it ->
            if (lastItem == null || isDifferrentDate(lastItem?.time ?: 0, it.time ?: 0)) {
                resultList.add(it.time ?: 0)
                lastItem?.typeItem = 2
                it.typeItem = 0
            }

            resultList.add(it.apply {
                it.amountUsd = String.format(
                    "%.2f",
                    (it.amount?.toDouble() ?: 0.0) * (rates)
                )

            })
            it.currency = currency
            lastItem = it
        }
        (resultList[resultList.size - 1] as? TransactionItem)?.typeItem = 2

        return resultList
    }

    private fun showResult(it: Boolean, balance: String? = null) {
        if (it) {
            makeTransactionDialog?.dismiss()
            AcceptDialog.newInstance(balance ?: "", "Success")
                .show(childFragmentManager, AcceptDialog.TAG)
        } else {
            //ErrorDialog.newInstance("Empty field").show(childFragmentManager, ErrorDialog.TAG)
        }
    }

    companion object {
        val TAG: String =
            com.coinsliberty.wallet.ui.transaction.TransactionFragment::class.java.name

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
