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

    val resultLiveData: MutableLiveData<BuySellResponse> = MutableLiveData()

    private var doBuySellJob: Job? = null

    fun doBuy(fiatCurrency: String, cryptoCurrency: String, amountFrom: String, amountTo: String) {
        doBuySellJob?.cancel()
        doBuySellJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponse(
                repository.buy(
                    fiatCurrency,
                    cryptoCurrency,
                    amountFrom,
                    amountTo
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }
    fun doSell(fiatCurrency: String, cryptoCurrency: String, amountFrom: String, amountTo: String) {
        doBuySellJob?.cancel()
        doBuySellJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponse(
                repository.sell(
                    fiatCurrency,
                    cryptoCurrency,
                    amountFrom,
                    amountTo
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    private fun handleResponse(response: BuySellResponse){
       // if (response.result == true){
            resultLiveData.postValue(response)
       // }
    }

    fun confirmBuy(operationId: Int, code: String){
        doBuySellJob?.cancel()
        doBuySellJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
                handleResponse(
                    repository.confirmBuy(operationId, code)
                )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun confirmSell(operationId: Int, code: String){
        doBuySellJob?.cancel()
        doBuySellJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
                handleResponse(
                    repository.confirmSell(operationId, code)
                )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    override fun stopRequest() {
        doBuySellJob?.cancel()
    }
}