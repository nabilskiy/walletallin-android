package com.example.coinsliberty.ui.wallet
import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.dialogs.AcceptDialog
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.dialogs.QrCodeDialog
import com.example.coinsliberty.dialogs.SendDialog
import com.example.coinsliberty.ui.wallet.data.WalletContent
import com.example.coinsliberty.utils.extensions.bindDataTo
import com.google.zxing.qrcode.encoder.QRCode
import kotlinx.android.synthetic.main.fragment_my_wallet.*
import org.koin.android.viewmodel.ext.android.viewModel


class MyWalletFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_my_wallet
    override val viewModel: MyWalletViewModel by viewModel()
    override val navigator: MyWalletNavigation = MyWalletNavigation()

    val adapter = BaseAdapter()
        .map(R.layout.item_wallet, MyWalletHolder{ navigator.goToTransactions(navController) })
        .map(R.layout.item_transaction, TransactionHolder())

    private val sendDialog = SendDialog
        .newInstance("Sent eth", "test", "100", "100", "USD", "EUR")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            subscribeLiveData()
            rvWallet.adapter = adapter

        walletToolbarSendButton.setOnClickListener {
            sendDialog
                .apply {
                    initListeners {
                        showResult(it)
                    }
                }
                .show(childFragmentManager, SendDialog.TAG)
        }
        walletToolbarRecieveButton.setOnClickListener {
            QrCodeDialog.newInstance("Sent eth", "test").show(childFragmentManager, QrCodeDialog.TAG)
        }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.walletLiveData, ::initLanguages)
    }


    private fun initLanguages(list: List<WalletContent>) {
        adapter.itemsLoaded(list)
        adapter.itemsAdded(listOf(Unit))
    }

    private fun showResult(it: Boolean) {
        if(it) {
            sendDialog.dismiss()
            AcceptDialog.newInstance("1000.00", "Success").show(childFragmentManager, AcceptDialog.TAG)
        } else {
            ErrorDialog.newInstance("Empty field").show(childFragmentManager, ErrorDialog.TAG)
        }
    }


    companion object { val TAG = MyWalletFragment::class.java.name }
}
