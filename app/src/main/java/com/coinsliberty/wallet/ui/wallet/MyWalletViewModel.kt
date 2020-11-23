package com.coinsliberty.wallet.ui.wallet
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.*
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.wallet.data.WalletContent
import com.coinsliberty.wallet.utils.wallets.Wallets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MyWalletViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: WalletRepository): BaseViewModel(app) {

    val walletLiveData: MutableLiveData<List<WalletContent>> = MutableLiveData()
    val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val balanceDataLiveData: MutableLiveData<BalanceInfoContent> = MutableLiveData()
    val availableBalanceDataLiveData: MutableLiveData<AvailableBalanceInfoContent> = MutableLiveData()

    var rates: Double? = null
    var balanceData: BalanceInfoContent? = null
    var wallet: Wallets? = null


    fun walletList() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.walletList(), repository.getBalance())
            handleTransactionResponse(repository.getTransactions())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun refreshData() {
        launch(::onErrorHandler) {
            delay(6000)
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.walletList(), repository.getBalance())
            handleTransactionResponse(repository.getTransactions())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleTransactionResponse(transactions: TransactionResponse) {
        transactionsLiveData.postValue(transactions.item?.filterIndexed { index, _ -> index < 5 })
    }

    private fun handleResponse(walletList: WalletInfoResponse, balance: BalanceInfoResponse) {
        if((walletList.result == false && walletList.error?.code == 1002) ||(balance.result == false && balance.error?.code == 1002)) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }

            return

        }

        walletLiveData.postValue(walletList.list?.map {
            wallet = getWallet(it.id)
            rates = balance.rates?.btc ?: 0.0
            balanceData = balance.balances
           // availableBalanceData = balance.availableBalances
            balanceDataLiveData.postValue(balance.balances)
            availableBalanceDataLiveData.postValue(balance.availableBalances)

            val balanceValue = if(it.locked == false) getValue(balance, it.id) else null

            WalletContent(
                wallet?.getImg() ?: 0,
                wallet?.getTitle() ?: "",
                it.label,
                if(balanceValue != null ) String.format("%.8f", balanceValue) + " " + it.label else null,
                if(balanceValue != null ) String.format("%.2f", (rates ?: 0.0) * balanceValue) + " $" else null,
                wallet?.getBackground() ?: 0
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