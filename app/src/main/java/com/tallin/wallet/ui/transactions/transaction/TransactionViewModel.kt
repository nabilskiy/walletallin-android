package com.tallin.wallet.ui.transactions.transaction

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.BalanceInfoResponse
import com.tallin.wallet.data.response.TransactionItem
import com.tallin.wallet.data.response.TransactionResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.ui.wallet.WalletRepository
import com.tallin.wallet.utils.currency.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: WalletRepository,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {


    val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val balanceLiveData: MutableLiveData<BalanceInfoResponse> = MutableLiveData()
    val currency: MutableLiveData<Currency> = MutableLiveData()

    var transactionJob: Job? = null
    var updateBalanceJob: Job? = null

    var walletType  = "BTC"

    override fun stopRequest() {
        transactionJob?.cancel()
        updateBalanceJob?.cancel()
    }

    fun getTransaction() {
        transactionJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            if (walletType == "BTC")
                handleTransactionResponse(repository.getTransactionsBtc())
            else handleTransactionResponse(repository.getTransactionsEth())
            handleBalanceResponse(repository.getBalance())
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun updateBalance() {
        updateBalanceJob = launch(::onErrorHandler) {
            delay(5000)
            handleBalanceResponse(repository.getBalance())
        }
    }

    fun getCurrency() {
        currency.postValue(sharedPreferencesProvider.getCurrency())
    }


    private fun handleTransactionResponse(transactions: TransactionResponse) {
        if (transactions.result == false && transactions.error?.code == 1002) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }
            return
        }
        transactionsLiveData.postValue(transactions.item)
    }

    private fun handleBalanceResponse(balance: BalanceInfoResponse) {
        balanceLiveData.postValue(balance)
    }
}