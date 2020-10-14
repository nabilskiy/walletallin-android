package com.example.coinsliberty.ui.wallet
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.ui.wallet.data.WalletContent

class MyWalletViewModel: BaseViewModel() {
    val walletLiveData: MutableLiveData<List<WalletContent>> = MutableLiveData(getListData())

    fun getListData(): ArrayList<WalletContent> {
        val listData: ArrayList<WalletContent> = ArrayList()

        listData.add(WalletContent(R.drawable.ic_bitcoin, R.string.bitcoin_wallet, R.string.bitcoin_abreviatoure,
                                    R.string.bitcoin_price, R.string.bitcoin_result, R.drawable.bg_wallet_item_light_orange))
        listData.add(WalletContent(R.drawable.ic_etherium, R.string.ethereum_wallet, R.string.ethereum_abreviatoure, null, null, R.drawable.bg_wallet_item_light_purple))
        listData.add(WalletContent(R.drawable.ic_litecoin, R.string.litecoin_wallet, R.string.litecoin_abreviatoure, null, null, R.drawable.bg_wallet_item_grey))
        listData.add(WalletContent(R.drawable.ic_ripple, R.string.ripple_wallet, R.string.ripple_abreviatoure, null, null, R.drawable.bg_wallet_item_light_blue))
        listData.add(WalletContent(R.drawable.ic_bitcoin_cash, R.string.bitcoin_cash_wallet, R.string.bitcoin_cash_abreviatoure,
            R.string.bitcoin_cash_price, R.string.bitcoin_cash_result, R.drawable.bg_wallet_item_light_green))
        listData.add(WalletContent(R.drawable.ic_dashcoin, R.string.dashcoin_wallet, R.string.dashcoin_abreviatoure,
            R.string.dashcoin_price, R.string.dashcoin_result, R.drawable.bg_wallet_item_middle_blue))

        return listData
    }
}