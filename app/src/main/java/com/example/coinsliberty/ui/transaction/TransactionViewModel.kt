package com.example.coinsliberty.ui.transaction

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.BalanceInfoResponse
import com.example.coinsliberty.data.TransactionItem
import com.example.coinsliberty.data.TransactionResponse
import com.example.coinsliberty.ui.wallet.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val app: Application,
    private val repository: WalletRepository
): BaseViewModel(app)  {


    val transactionsLiveData: MutableLiveData<List<TransactionItem>> = MutableLiveData()
    val availableBalanceLiveData: MutableLiveData<Double> = MutableLiveData()

    fun getTransaction() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleTransactionResponse(repository.getTransactions())
            handleBalanceResponse(repository.getBalance())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }


    private fun handleTransactionResponse(transactions: TransactionResponse) {
        transactionsLiveData.postValue(transactions.item)
    }

    private fun handleBalanceResponse(balance: BalanceInfoResponse) {
        availableBalanceLiveData.postValue(balance.availableBalances?.btc)
    }
}