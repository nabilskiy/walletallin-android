package com.tallin.wallet.ui.actions.orderPreview

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.BuySellResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class OrderPreviewViewModel (
    app: Application,
    private val repository: OrderPreviewRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val resultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var doBuyJob: Job? = null
    private var doSellJob: Job? = null

    fun doBuy(cryptoCurrency: String, cryptoAmount: Double) {
        doBuyJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponse(
                repository.buy(
                    cryptoCurrency,
                    cryptoAmount
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }
    fun doSell(fiatCurrency: String, fiatAmount: Double) {
        doBuyJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponse(
                repository.buy(
                    fiatCurrency,
                    fiatAmount
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    private fun handleResponse(response: BuySellResponse){
        if (response.result == true){
            resultLiveData.postValue(true)
        }
    }


    override fun stopRequest() {
        doBuyJob?.cancel()
        doSellJob?.cancel()
    }
}