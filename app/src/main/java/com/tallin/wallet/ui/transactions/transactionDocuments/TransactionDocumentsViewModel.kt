package com.tallin.wallet.ui.transactions.transactionDocuments

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.TransactionDocumentsInfoResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TransactionDocumentsViewModel(
    app: Application,
    private val repository: TransactionDocumentsRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val transactionDocsInfoLiveData: MutableLiveData<TransactionDocumentsInfoResponse> = MutableLiveData()

    private var getTransactionDocsInfoJob: Job? = null

    fun getTransactionDocsInfo(id: Int){
        //getTransactionDocsInfoJob?.cancel()
        getTransactionDocsInfoJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(
                repository.getTransactionDocumentsInfo(id)
            )
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun handleResponse(response: TransactionDocumentsInfoResponse){
        if (
            (response.result == false && response.error?.code == 1002)
        ) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }
            return
        } else {
            transactionDocsInfoLiveData.postValue(response)
        }
    }

    override fun stopRequest() {
        getTransactionDocsInfoJob?.cancel()
    }
}