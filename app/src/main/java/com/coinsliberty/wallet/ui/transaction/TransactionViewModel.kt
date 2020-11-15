package com.coinsliberty.wallet.ui.transaction

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.BalanceInfoResponse
import com.coinsliberty.wallet.data.response.TransactionItem
import com.coinsliberty.wallet.data.response.TransactionResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.wallet.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: WalletRepository
): BaseViewModel(app)  {


    val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val balanceLiveData: MutableLiveData<BalanceInfoResponse> = MutableLiveData()

    fun getTransaction() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleTransactionResponse(repository.getTransactions())
            handleBalanceResponse(repository.getBalance())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
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