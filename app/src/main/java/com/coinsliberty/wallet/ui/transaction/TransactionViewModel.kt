package com.coinsliberty.wallet.ui.transaction

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.BalanceInfoResponse
import com.coinsliberty.wallet.data.response.TransactionItem
import com.coinsliberty.wallet.data.response.TransactionResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.ui.wallet.WalletRepository
import com.coinsliberty.wallet.utils.currency.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: WalletRepository,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository)  {


    val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val balanceLiveData: MutableLiveData<BalanceInfoResponse> = MutableLiveData()
    val currency: MutableLiveData<Currency> = MutableLiveData()

    var transactionJob: Job? = null
    var updateBalanceJob: Job? = null

    override fun stopRequest() {
        transactionJob?.cancel()
        updateBalanceJob?.cancel()
    }

    fun getTransaction() {
        transactionJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleTransactionResponse(repository.getTransactions())
            handleBalanceResponse(repository.getBalance())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
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
        if(transactions.result == false && transactions.error?.code == 1002) {
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