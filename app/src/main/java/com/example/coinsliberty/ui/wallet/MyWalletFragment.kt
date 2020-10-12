package com.example.coinsliberty.ui.wallet
import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.ui.wallet.data.WalletContent
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_my_wallet.*


class MyWalletFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_my_wallet
    override val viewModel: MyWalletViewModel = MyWalletViewModel()
    override val navigator: MyWalletNavigation = MyWalletNavigation()

    val adapter = BaseAdapter().map(R.layout.item_wallet, MyWalletHolder{})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            subscribeLiveData()
            rvWallet.adapter = adapter
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.walletLiveData, ::initLanguages)
    }


    private fun initLanguages(list: List<WalletContent>) {
        adapter.itemsLoaded(list)
    }

    private fun navigate() {}

    companion object { val TAG = MyWalletFragment::class.java.name }
}
