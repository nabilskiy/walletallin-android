package com.example.coinsliberty.ui.wallet
import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.data.BalanceInfoContent
import com.example.coinsliberty.dialogs.AcceptDialog
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.dialogs.QrCodeDialog
import com.example.coinsliberty.dialogs.SendDialog
import com.example.coinsliberty.ui.transaction.TransactionFragment
import com.example.coinsliberty.ui.wallet.data.WalletContent
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_my_wallet.*
import org.koin.android.viewmodel.ext.android.viewModel

class MyWalletFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_my_wallet
    override val viewModel: MyWalletViewModel by viewModel()
    override val navigator: MyWalletNavigation = MyWalletNavigation()

    val adapter = BaseAdapter()
        .map(R.layout.item_wallet, MyWalletHolder{ navigator.goToTransactions(navController, TransactionFragment.getBundle(viewModel.rates, viewModel.balanceData?.btc, it.ico)) })
        .map(R.layout.item_transaction, TransactionHolder())

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
    }


    private fun initData(list: List<WalletContent>) {
        adapter.itemsLoaded(list)
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
