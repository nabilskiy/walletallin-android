package com.tallin.wallet.ui.wallet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.*
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.ui.wallet.data.WalletContent
import com.tallin.wallet.utils.currency.Currency
import com.tallin.wallet.utils.wallets.Wallets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// TODO: 17.02.2021 remove logs

const val WALLET_VIEW_MODEL_TAG = "WALLET_VIEW_MODEL"

class MyWalletViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: WalletRepository,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val walletLiveData: MutableLiveData<List<WalletContent>> = MutableLiveData()
    val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val balanceDataLiveData: MutableLiveData<BalanceInfoContent> = MutableLiveData()
    val availableBalanceDataLiveData: MutableLiveData<AvailableBalanceInfoContent> =
        MutableLiveData()
    val currency: MutableLiveData<Currency> = MutableLiveData()

    var btcRates: Double? = null
    var ethRates: Double? = null
    var balanceData: BalanceInfoContent? = null
    var wallet: Wallets? = null

    var walletListJob: Job? = null
    var refreshDataJob: Job? = null

    val TAG = WALLET_VIEW_MODEL_TAG

    override fun stopRequest() {
        walletListJob?.cancel()
        refreshDataJob?.cancel()
    }

    fun walletList() {
        walletListJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponse(repository.walletList(), repository.getBalance())
            handleTransactionResponse(repository.getTransactionsBtc())
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun refreshData() {
        refreshDataJob?.cancel()
//        Log.i(TAG, "refreshData: ")
        refreshDataJob = launch(::onErrorHandler) {
            delay(6000)
            handleResponse(repository.walletList(), repository.getBalance())
            handleTransactionResponse(repository.getTransactionsBtc())
        }
    }

    private fun handleTransactionResponse(transactions: TransactionResponse) {
        transactionsLiveData.postValue(transactions.item?.filterIndexed { index, _ -> index < 5 })
    }

    private fun handleResponse(walletList: WalletInfoResponse, balance: BalanceInfoResponse) {
        if ((walletList.result == false && walletList.error?.code == 1002) || (balance.result == false && balance.error?.code == 1002)) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }

            return

        }

        walletLiveData.postValue(walletList.list?.map {
            val currency = sharedPreferencesProvider.getCurrency()
            wallet = getWallet(it.id)
            btcRates = if (currency == null || currency == Currency.USD) {
                balance.rates?.btc?.usd ?: 0.0
            } else {
                balance.rates?.btc?.eur ?: 0.0
            }
            ethRates = if (currency == null || currency == Currency.USD) {
                balance.rates?.eth?.usd ?: 0.0
            } else {
                balance.rates?.eth?.eur ?: 0.0
            }
            balanceData = balance.balances
            // availableBalanceData = balance.availableBalances
            balanceDataLiveData.postValue(balance.balances)
            availableBalanceDataLiveData.postValue(balance.availableBalances)

            val balanceValue = if (it.locked == false) getValue(balance, it.id) else null

            WalletContent(
                wallet?.getImg() ?: 0,
                wallet?.getTitle() ?: "",
                it.label,
                if (balanceValue != null) String.format(
                    "%.8f",
                    balanceValue
                ) + " " + it.label else null,
                if (balanceValue != null) String.format(
                    "%.2f",
                    (btcRates ?: 0.0) * balanceValue
                ) + if (currency == null || currency == Currency.USD) " $" else " €" else null,
                wallet?.getBackground() ?: 0,
                wallet?.getColor() ?: 0,
                wallet?.getFeeCoefficient() ?: 1.0,
                it.locked ?: true
            )
        })
    }

    fun getCurrency() {
        currency.postValue(sharedPreferencesProvider.getCurrency())
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