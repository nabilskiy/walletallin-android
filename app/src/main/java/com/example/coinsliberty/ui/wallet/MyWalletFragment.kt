package com.example.coinsliberty.ui.wallet
import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.data.response.AvailableBalanceInfoContent
import com.example.coinsliberty.data.response.BalanceInfoContent
import com.example.coinsliberty.data.response.TransactionItem
import com.example.coinsliberty.dialogs.AcceptDialog
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.dialogs.qrCode.QrCodeDialog
import com.example.coinsliberty.dialogs.sendDialog.SendDialog
import com.example.coinsliberty.ui.transaction.TransactionFragment
import com.example.coinsliberty.ui.wallet.adapters.MyWalletHolder
import com.example.coinsliberty.ui.wallet.adapters.TransactionDataHolder
import com.example.coinsliberty.ui.wallet.adapters.TransactionHolder
import com.example.coinsliberty.ui.wallet.adapters.TransactionTitleHolder
import com.example.coinsliberty.ui.wallet.data.WalletContent
import com.example.coinsliberty.utils.extensions.bindDataTo
import com.example.coinsliberty.utils.isDifferrentDate
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
            resultList.add(it.apply { it.amountUsd = ((it.amount?.toDouble() ?: 0.0) * (viewModel.rates ?: 0.0)).toString() })
        }
        return resultList
    }


    private fun initData(list: List<WalletContent>) {
        //walletToolbarTitle.text = (list.firstOrNull()?.result ?: 0.0).toString() + " USD"
        adapter.itemsLoaded(list)
        adapter.itemsAdded(listOf("Last Transactions"))

    }

    private fun initBalance(balance : BalanceInfoContent){
        tvTotalBalanceCrypto.text = String.format("%.8f",balance.btc ?: 0.0)
        tvTotalBalanceFiat.text = String.format("%.8f", ((balance.btc?: 0.0) * (viewModel.rates ?: 0.0)))

    }

    private fun initAvailableBalance(balance : AvailableBalanceInfoContent){
        tvAvailableCrypto.text = String.format("%.8f",balance.btc ?: 0.0)
        tvAvailableFiat.text = String.format("%.8f", ((balance.btc?: 0.0) * (viewModel.rates ?: 0.0)))
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
