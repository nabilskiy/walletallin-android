package com.coinsliberty.wallet.ui.wallet
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseAdapter
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.data.response.AvailableBalanceInfoContent
import com.coinsliberty.wallet.data.response.BalanceInfoContent
import com.coinsliberty.wallet.data.response.TransactionItem
import com.coinsliberty.wallet.dialogs.AcceptDialog
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.dialogs.sendDialog.SendDialog
import com.coinsliberty.wallet.ui.transaction.TransactionFragment
import com.coinsliberty.wallet.ui.wallet.adapters.MyWalletHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionDataHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionTitleHolder
import com.coinsliberty.wallet.ui.wallet.data.WalletContent
import com.coinsliberty.wallet.utils.currency.Currency
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.coinsliberty.wallet.utils.isDifferrentDate
import kotlinx.android.synthetic.main.fragment_my_wallet.*
import kotlinx.coroutines.flow.merge
import org.koin.android.viewmodel.ext.android.viewModel


class MyWalletFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_my_wallet
    override val viewModel: MyWalletViewModel by viewModel()
    override val navigator: MyWalletNavigation = MyWalletNavigation()

    val adapter = BaseAdapter()
        .map(R.layout.item_wallet, MyWalletHolder {
            navigator.goToTransactions(
                navController, TransactionFragment.getBundle(
                    viewModel.rates,
                    viewModel.balanceData?.btc,
                    it.ico
                )
            )
        })
        .map(R.layout.item_transaction, TransactionHolder())
        .map(R.layout.item_data, TransactionDataHolder())
        .map(R.layout.item_title, TransactionTitleHolder())

    private var sendDialog: SendDialog? = null

    private var walletData: List<WalletContent>? = null

    private var currency = Currency.USD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()
    }

    override fun onStart() {
        super.onStart()
        rvWallet.adapter = null
        adapter.removeAll()
        rvWallet.adapter = adapter
        viewModel.getCurrency()
        viewModel.walletList()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.walletLiveData, ::initData)
        bindDataTo(viewModel.transactionsLiveData, ::initTransactions)
        bindDataTo(viewModel.balanceDataLiveData, ::initBalance)
        bindDataTo(viewModel.availableBalanceDataLiveData, ::initAvailableBalance)
        bindDataTo(viewModel.currency, ::initCurrency)
    }

    private fun initCurrency(currency: Currency?) {
        if(currency == null) return
        this.currency = currency

        tvFiatName.text = currency.getTitle()
        tvTotalBalanceFiatName.text = currency.getTitle()
    }

    private fun initTransactions(list: List<TransactionItem>?) {
        adapter.itemsLoaded(((walletData ?: emptyList()) + listOf("Last Transactions") + (getTransactions(list) ?: emptyList())))

        viewModel.refreshData()
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
                    (it.amount?.toDouble() ?: 0.0) * (viewModel.rates ?: 0.0)
                )

            })

            it.currency = currency

            lastItem = it
        }
        (resultList[resultList.size - 1] as? TransactionItem)?.typeItem = 2

        return resultList
    }


    private fun initData(list: List<WalletContent>) {
        walletData = list

    }

    private fun initBalance(balance: BalanceInfoContent){
        tvTotalBalanceCrypto.text = String.format("%.8f", balance.btc ?: 0.0)
        tvTotalBalanceFiat.text = String.format(
            "%.2f",
            ((balance.btc ?: 0.0) * (viewModel.rates ?: 0.0))
        )

    }

    private fun initAvailableBalance(balance: AvailableBalanceInfoContent){
        tvBalanceCrypto.text = String.format("%.8f", balance.btc ?: 0.0)
        tvBalanceFiat.text = String.format(
            "%.2f",
            ((balance.btc ?: 0.0) * (viewModel.rates ?: 0.0))
        )
    }

    private fun showResult(it: Boolean, balance: String? = null) {
        if(it) {
            sendDialog?.dismiss()
            AcceptDialog.newInstance(balance ?: "", "Success").show(
                childFragmentManager,
                AcceptDialog.TAG
            )
        } else {
            ErrorDialog.newInstance("Empty field").show(childFragmentManager, ErrorDialog.TAG)
        }
    }


    companion object {
        val TAG = MyWalletFragment::class.java.name
    }
}
