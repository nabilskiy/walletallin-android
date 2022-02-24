package com.tallin.wallet.ui.actions.buy

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.requests.RateRequest
import com.tallin.wallet.data.response.*
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.ui.wallet.data.RateContent
import com.tallin.wallet.ui.wallet.data.WalletContent
import com.tallin.wallet.utils.currency.Currency
import com.tallin.wallet.utils.wallets.Wallets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class BuySellViewModel (
    app: Application,
    private val repository: BuySellRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    var balanceData: BalanceInfoContent? = null

    var btcRates: Double? = null
    var ethRates: Double? = null

    var wallet: Wallets? = null

    val walletLiveData: MutableLiveData<List<WalletContent>> = MutableLiveData()
    //val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val balanceDataLiveData: MutableLiveData<BalanceInfoContent> = MutableLiveData()
    val availableBalanceDataLiveData: MutableLiveData<AvailableBalanceInfoContent> =
        MutableLiveData()
    val currency: MutableLiveData<Currency> = MutableLiveData()
    val rateLiveData: MutableLiveData<RateContent> = MutableLiveData()

    private var refreshDataJob: Job? = null
    private var walletListJob: Job? = null
    private var getRateJob: Job? = null

    fun refreshData(fiatCurrency: String, cryptoCurrency: String) {
        refreshDataJob?.cancel()
        if(walletListJob?.isActive != true) {
            refreshDataJob = launch(::onErrorHandler) {
                handleRate(
                    repository.getRate(
                        RateRequest(fiatCurrency, cryptoCurrency)
                    )
                )
            }
        }
    }

    fun walletList(fiatCurrency: String, cryptoCurrency: String) {
        walletListJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponse(
                repository.walletList(),
                repository.getBalance(),
                repository.getRate(
                    RateRequest(fiatCurrency, cryptoCurrency)
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    private fun handleResponse(
        walletList: WalletInfoResponse,
        balance: BalanceInfoResponse,
        rate: RateResponse
    ) {
        if (
            (walletList.result == false && walletList.error?.code == 1002) ||
            (balance.result == false && balance.error?.code == 1002) ||
            (rate.result == false && rate.error?.code == 1002)
        ) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }

            return

        }
        try {
            rateLiveData.postValue(
                RateContent(
                    rate.rate!!.toDouble(),
                    rate.updatedAt!!
                )
            )
        } catch (e: Exception){
            showError.postValue("Server error: ${e.message}")
        }
        walletLiveData.postValue(walletList.list?.map {
            val currency = sharedPreferencesProvider.getCurrency()
            wallet = getWallet(it.id)
            btcRates = if (currency == Currency.USD) {
                balance.rates?.btc?.usd ?: 0.0
            } else {
                balance.rates?.btc?.eur ?: 0.0
            }
            ethRates = if (currency == Currency.USD) {
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
                it.name,
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
                it.locked ?: true,
                crypto = it.crypto ?: true
            )
        })
    }

    private fun handleRate(rate: RateResponse){
        if (
            (rate.result == false && rate.error?.code == 1002)
        ) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }
            return
        }
        try {
            rateLiveData.postValue(
                RateContent(
                    rate.rate!!.toDouble(),
                    rate.updatedAt!!
                )
            )
        } catch (e: Exception){
            showError.postValue("Server error: ${e.message}")
        }
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

    private fun getWallet(string: String?) =
        when (string) {
            "btc" -> {
                Wallets.BITCOIN_WALLET
            }
            "eth" -> {
                Wallets.ETHEREUM_WALLET
            }
            else -> {
                Wallets.TETHER_WALLET
            }
        }

    override fun stopRequest() {
        refreshDataJob?.cancel()
        walletListJob?.cancel()
        getRateJob?.cancel()
    }
}