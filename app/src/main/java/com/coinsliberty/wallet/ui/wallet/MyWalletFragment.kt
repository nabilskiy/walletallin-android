package com.coinsliberty.wallet.ui.wallet
import android.os.Bundle
import android.view.View
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseAdapter
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.data.response.AvailableBalanceInfoContent
import com.coinsliberty.wallet.data.response.BalanceInfoContent
import com.coinsliberty.wallet.data.response.TransactionItem
import com.coinsliberty.wallet.dialogs.AcceptDialog
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.dialogs.qrCode.QrCodeDialog
import com.coinsliberty.wallet.dialogs.sendDialog.SendDialog
import com.coinsliberty.wallet.ui.transaction.TransactionFragment
import com.coinsliberty.wallet.ui.wallet.adapters.MyWalletHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionDataHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionHolder
import com.coinsliberty.wallet.ui.wallet.adapters.TransactionTitleHolder
import com.coinsliberty.wallet.ui.wallet.data.WalletContent
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.coinsliberty.wallet.utils.isDifferrentDate
import kotlinx.android.synthetic.main.fragment_my_wallet.*
import org.koin.android.viewmodel.ext.android.viewModel

class MyWalletFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_my_wallet
    override val viewModel: MyWalletViewModel by viewModel()
    override val navigator: MyWalletNavigation = MyWalletNavigation()

    val adapter = BaseAdapter()
        .map(R.layout.item_wallet, MyWalletHolder{ navigator.goToTransactions(navController, TransactionFragment.getBundle(viewModel.rates, viewModel.balanceData?.btc, it.ico)) })
        .map(R.layout.item_transaction, TransactionHolder())
        .map(R.layout.item_data, TransactionDataHolder())
        .map(R.layout.item_title, TransactionTitleHolder())

    private var sendDialog: SendDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            subscribeLiveData()
            rvWallet.adapter = adapter

        walletToolbarSendButton.setOnClickListener {
            if(sendDialog == null) {
                sendDialog = SendDialog.newInstance("Sent eth", viewModel.rates ?: 0.0, viewModel.balanceData?.btc ?: 0.0)
            }

            sendDialog
                ?.apply {
                    initListeners { result, text ->
                        showResult(result, text)
                    }
                }
                ?.show(childFragmentManager, SendDialog.TAG)
        }
        walletToolbarRecieveButton.setOnClickListener {
            QrCodeDialog.newInstance("Sent btc", "test").show(childFragmentManager, QrCodeDialog.TAG)
        }
        viewModel.walletList()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.walletLiveData, ::initData)
        bindDataTo(viewModel.transactionsLiveData, ::initTransactions)
        bindDataTo(viewModel.balanceDataLiveData, ::initBalance)
        bindDataTo(viewModel.availableBalanceDataLiveData, ::initAvailableBalance)
    }

    private fun initTransactions(list: List<TransactionItem>?) {
        adapter.itemsAdded(getTransactions(list))
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
            resultList.add(it.apply { it.amountUsd = String.format("%.2f", (it.amount?.toDouble() ?: 0.0) * (viewModel.rates ?: 0.0)) })
        }
        return resultList
    }


    private fun initData(list: List<WalletContent>) {
        //walletToolbarTitle.text = (list.firstOrNull()?.result ?: 0.0).toString() + " USD"
        adapter.itemsLoaded(list)
        adapter.itemsAdded(listOf("Last Transactions"))

    }

    private fun initBalance(balance : BalanceInfoContent){
        tvTotalBalanceCrypto.text = String.format("%.4f",balance.btc ?: 0.0)
        tvTotalBalanceFiat.text = String.format("%.4f", ((balance.btc?: 0.0) * (viewModel.rates ?: 0.0)))

    }

    private fun initAvailableBalance(balance : AvailableBalanceInfoContent){
        tvAvailableCrypto.text = String.format("%.4f",balance.btc ?: 0.0)
        tvAvailableFiat.text = String.format("%.4f", ((balance.btc?: 0.0) * (viewModel.rates ?: 0.0)))
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
        val TAG = MyWalletFragment::class.java.name
    }
}
