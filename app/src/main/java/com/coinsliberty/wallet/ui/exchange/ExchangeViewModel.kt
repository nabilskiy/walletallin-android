package com.coinsliberty.wallet.ui.exchange

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.BuildConfig
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.SwapRequestBody
import com.coinsliberty.wallet.data.response.*
import com.coinsliberty.wallet.dialogs.makeTransaction.MakeTransactionRepository
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.ui.wallet.WalletRepository
import com.coinsliberty.wallet.ui.wallet.data.WalletContent
import com.coinsliberty.wallet.utils.currency.Currency
import com.coinsliberty.wallet.utils.liveData.SingleLiveData
import com.coinsliberty.wallet.utils.wallets.Wallets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ExchangeViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository,
    private val transactionRepository: MakeTransactionRepository,
    private val walletRepository: WalletRepository,
    private val exchangeRepository: ExchangeRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    private val TAG = ExchangeViewModel::class.java.name


    val walletLiveData: MutableLiveData<List<WalletContent>> = MutableLiveData()
    val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val balanceDataLiveData: MutableLiveData<BalanceInfoContent> = MutableLiveData()
    val availableBalanceDataLiveData = AvailableBalanceLiveData()
    val swapLimitsData = MutableLiveData<SwapLimitsData>()
    val swapInfoData = MutableLiveData<SwapInfoData>()
    val currency: MutableLiveData<Currency> = MutableLiveData()
    val walletFrom = MutableLiveData<WalletContent>()
    val walletTo = MutableLiveData<WalletContent>()
    val result: SingleLiveData<Boolean> = SingleLiveData()

    var fromRates: Double? = null

    // TODO: 24.02.2021 change eth to any currency from "to" field
    var toRates: Double? = null

    var balanceData: BalanceInfoContent? = null
    private var networkFee: Double = 0.000007

    private var walletListJob: Job? = null
    private var refreshDataJob: Job? = null
    private var swapInfoJob: Job? = null
    private var makeSwapJob: Job? = null


    private var counterTryGetLimits = 0


    var wallet: Wallets? = null

    fun initData() {
        val reverse =
            if (walletFrom.value == null || walletTo.value == null)
                false
            else walletFrom.value?.type == "btc"
        counterTryGetLimits = 0
        walletListJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleWalletResponse(
                walletRepository.walletList(),
                walletRepository.getBalance(),
                reverse
            )
            handleResponseFee(transactionRepository.getFee(walletFrom.value?.type ?: ""))
            getSwapLimits()
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun makeExchange() {
        makeSwapJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleSwapResponse(
                exchangeRepository.doSwap(
                    SwapRequestBody(swapInfoData.value?.id!!, networkFee.toString())
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    private fun handleResponseFee(fee: FeeResponse) {
        if (!BuildConfig.DEBUG)
            networkFee = (fee.rates?.mid ?: 0.0) * 0.00000001 * 240
    }

    fun getSwapInfo(amount: Double) {
        val from = walletFrom.value?.title ?: return
        val to = walletTo.value?.title ?: return
        swapInfoJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleSwapInfoResponse(
                exchangeRepository.getExchangeInfo(
                    from,
                    to,
                    amount.toString(),
                    null
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    private fun handleSwapResponse(response: SwapResultResponse) {
        if (response.result == true) {
            result.postValue(true)
        } else {
            showError.postValue(response.error?.message ?: "")
            result.postValue(false)
        }
    }

    private fun handleSwapInfoResponse(info: SwapInfoResponse) {
        if (!info.result) {
            showError.postValue("error")
            return
        }
        swapInfoData.postValue(info.info)
    }

    fun swapCryptos() {
        fromRates = toRates.also { toRates = fromRates }
        availableBalanceDataLiveData.swap()
        walletTo.value = walletFrom.value.also { walletFrom.value = walletTo.value }
        getSwapLimits()
    }

    fun setWalletTo(wallet: WalletContent) {
        if (wallet == walletTo.value || wallet.type == walletTo.value?.type) {
            return
        } else if (wallet == walletFrom.value || wallet.type == walletFrom.value?.type) {
            swapCryptos()
        } else {
            walletTo.postValue(wallet)
        }
    }

    fun setWalletFrom(wallet: WalletContent) {
        if (wallet == walletFrom.value || wallet.type == walletFrom.value?.type) {
            return
        } else if (wallet == walletTo.value || wallet.type == walletTo.value?.type) {
            swapCryptos()
        } else {
            walletFrom.postValue(wallet)
        }
    }

    private fun getSwapLimits() {
        if (counterTryGetLimits > 3)
            return
        refreshDataJob = launch(::onErrorHandler) {
            delay(200)
            if (walletTo.value == null || walletFrom.value == null) {
                counterTryGetLimits++
                getSwapLimits()
            } else {
                withContext(Dispatchers.Main) { onStartProgress.value = Unit }
                handleSwapLimits(
                    exchangeRepository.getLimits(
                        walletFrom.value?.title!!,
                        walletTo.value?.title!!
                    )
                )
                withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            }
        }
    }

    private fun handleSwapLimits(response: SwapLimitsResponse) {
        if (!response.result)
            return
        swapLimitsData.postValue(response.limitsData)
    }


    private fun handleWalletResponse(
        walletList: WalletInfoResponse,
        balance: BalanceInfoResponse,
        reverse: Boolean
    ) {
        if ((walletList.result == false && walletList.error?.code == 1002) || (balance.result == false && balance.error?.code == 1002)) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }
            return
        }

        walletLiveData.postValue(
            walletList.list?.map {
                val currency = sharedPreferencesProvider.getCurrency()
                wallet = getWallet(it.id)
                fromRates = if (currency == null || currency == Currency.USD) {
                    balance.rates?.btc?.usd ?: 0.0
                } else {
                    balance.rates?.btc?.eur ?: 0.0
                }
                toRates = if (currency == null || currency == Currency.USD) {
                    balance.rates?.eth?.usd ?: 0.0
                } else {
                    balance.rates?.eth?.eur ?: 0.0
                }

                if (reverse) fromRates = toRates.also { toRates = fromRates }
                balanceData = balance.balances
                availableBalanceDataLiveData.setValue(balance.availableBalances, reverse)
                balanceDataLiveData.postValue(balance.balances)

                val balanceValue = if (it.locked == false) getValue(balance, it.id) else null

                val walletContent = WalletContent(
                    wallet?.getImg() ?: 0,
                    it.label ?: "",
                    it.name,
                    if (balanceValue != null) String.format(
                        "%.8f",
                        balanceValue
                    ) + " " + it.label else null,
                    if (balanceValue != null) String.format(
                        "%.2f",
                        (fromRates ?: 0.0) * balanceValue
                    ) + if (currency == null || currency == Currency.USD) " $" else " €" else null,
                    wallet?.getBackground() ?: 0,
                    wallet?.getColor()!!,
                    wallet?.getFeeCoefficient()!!
                )


                if (wallet == Wallets.BITCOIN_WALLET) {
                    if (walletFrom.value == null) {
                        walletFrom.postValue(walletContent)
                    }
                } else
                    if (wallet == Wallets.ETHEREUM_WALLET) {
                        Log.i(TAG, "handleWalletResponse: ETH")
                        if (walletTo.value == null) {
                            Log.i(TAG, "handleWalletResponse: to == null")
                            walletTo.postValue(walletContent)
                        }
                    }

                walletContent
            })
    }

    fun getCurrency() {
        currency.postValue(sharedPreferencesProvider.getCurrency())
    }


    override fun stopRequest() {
        walletListJob?.cancel()
        refreshDataJob?.cancel()
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