package com.example.coinsliberty.ui.wallet
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.BalanceInfoContent
import com.example.coinsliberty.data.BalanceInfoResponse
import com.example.coinsliberty.data.SignUpRequest
import com.example.coinsliberty.data.WalletInfoResponse
import com.example.coinsliberty.ui.wallet.data.WalletContent
import com.example.coinsliberty.utils.wallets.Wallets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWalletViewModel(private val app: Application, private val repository: WalletRepository): BaseViewModel(app) {
    val walletLiveData: MutableLiveData<List<WalletContent>> = MutableLiveData()

    var rates: Double? = null
    var balanceData: BalanceInfoContent? = null
//    fun getListData(): ArrayList<WalletContent> {
//        val listData: ArrayList<WalletContent> = ArrayList()
//        listData.add(WalletContent(R.drawable.ic_bitcoin, R.string.bitcoin_wallet, R.string.bitcoin_abreviatoure, R.string.bitcoin_price, R.string.bitcoin_result, R.color.lightOrange))
//        return listData
//    }
//
    fun walletList() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.walletList(), repository.getBalance())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(walletList: WalletInfoResponse, balance: BalanceInfoResponse) {
        walletLiveData.postValue(walletList.list?.map {
            val wallet = getWallet(it.id)
            rates = balance.rates?.btc ?: 0.0
            balanceData = balance.balances

            Log.e("!!!", rates.toString())
            Log.e("!!!", balanceData.toString())
            val balanceValue = if(it.locked == false) getValue(balance, it.id) else null

            WalletContent(
                wallet.getImg(),
                wallet.getTitle(),
                it.label,
                if(balanceValue != null ) balanceValue.toString() + " " + it.label else null,
                if(balanceValue != null ) String.format("%.2f", rates) + " $" else null,
                wallet.getBackground()
            )
        })
    }

    fun getValue(balance: BalanceInfoResponse, string: String?) =
        when (string) {
            "btc" -> {
                balance.balances?.btc ?: 0.0
            }
            "eth" -> {
                balance.balances?.eth ?: 0.0
            }
            else -> {
                balance.balances?.usdt ?: 0.0
            }
        }

    fun getWallet(string: String?) =
        when (string) {
            "btc" -> {
                Wallets.BITCOIN_WALLET
            }
            "eth" -> {
                Wallets.ETHEREUM_WALLET
            }
            else -> {
                Wallets.LITECOIN_WALLET
            }
        }


}