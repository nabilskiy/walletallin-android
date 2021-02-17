package com.coinsliberty.wallet.dialogs.makeTransaction

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.response.*
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.ui.wallet.WALLET_VIEW_MODEL_TAG
import com.coinsliberty.wallet.utils.crypto.encryptData
import com.coinsliberty.wallet.utils.currency.Currency
import com.coinsliberty.wallet.utils.liveData.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*

class MakeTransactionViewModel(
    private val app: Application,
    private val repository: MakeTransactionRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val result: SingleLiveData<Boolean> = SingleLiveData()
    val resultRecovery = MutableLiveData<String>()
    val feeInit = MutableLiveData<Rates>()
    val messageError = MutableLiveData<String>()
    val maxAvailable = MutableLiveData<SendMaxResponse>()
    val currency = MutableLiveData<Currency>()

    var sendBtcJob: Job? = null
    var updateDataJob: Job? = null
    var refreshFeeJob: Job? = null
    var sendMaxJob: Job? = null

    override fun stopRequest() {
        sendBtcJob?.cancel()
        updateDataJob?.cancel()
        refreshFeeJob?.cancel()
        sendMaxJob?.cancel()
    }

    fun sendBtc(
        asset: String,
        amount: String,
        address: String,
        fee: String,
        replaceable: Boolean
    ) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponseSend(
                repository.sendBtcBalance(
                    BtcBalance(
                        asset,
                        amount.replace(",", ".", true),
                        address,
                        encryptData(sharedPreferencesProvider.getPassword()) ?: "",
                        fee.replace(",", ".", true),
                        replaceable
                    )
                )
            )
            Log.e("!!!sendBtc", "2")
            //withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            Log.e("!!!sendBtc", "3")
        }
    }






    fun getCurrency() {
        currency.postValue(sharedPreferencesProvider.getCurrency())
    }

    fun updateData() {
        updateDataJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponseReceive(repository.getAddress())
            handleResponseFee(repository.getFee())
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun refreshFee() {
        refreshFeeJob = launch(::onErrorHandler) {
            delay(5000)
            handleResponseFee(repository.getFee())
        }
    }

    fun sendMax(asset: String, rate: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            val data = repository.sendMax(asset, rate.replace(",", ".", true))

            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            if(data.result == false) {
                messageError.postValue(data.error?.message ?: "Error")
            } else {
                handleResponseSendMax(data)
            }

        }
    }

    private fun handleResponseFee(fee: FeeResponse) {
        Log.i(WALLET_VIEW_MODEL_TAG, "handleResponseFee: ${fee.rates?.min}")
        feeInit.postValue(fee.rates)
    }

    private fun handleResponseSendMax(sendMax: SendMaxResponse) {
        Log.e("!!!", sendMax.toString())

        maxAvailable.postValue(sendMax)
    }

    private fun handleResponseSend(signUp: SignUpResponse) {
        if (signUp.result == true) {
            result.postValue(true)
            return
        } else {
            showError.postValue(signUp.error?.message.toString())
            result.postValue(false)
            return
        }
    }

    private fun handleResponseReceive(address: AddressInfoResponse) {
        if (address.result == true) {
            resultRecovery.postValue(address.address)
            return
        }
        showError.postValue(address.error?.message ?: "Error")
    }
}
