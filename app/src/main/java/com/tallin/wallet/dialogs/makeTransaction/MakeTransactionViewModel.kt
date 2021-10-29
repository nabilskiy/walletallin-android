package com.tallin.wallet.dialogs.makeTransaction

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.BtcBalance
import com.tallin.wallet.data.response.*
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.utils.crypto.encryptData
import com.tallin.wallet.utils.currency.Currency
import com.tallin.wallet.utils.liveData.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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

    private val TAG = MakeTransactionViewModel::class.java.name

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
        Log.i(TAG, "sendBtc: $fee")
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


    fun getCurrency(): Currency {
        return sharedPreferencesProvider.getCurrency()
    }

    fun updateData(asset: String) {
        updateDataJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponseReceive(repository.getAddress(asset))
            handleResponseFee(repository.getFee(asset))
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun refreshFee(asset: String) {
        refreshFeeJob = launch(::onErrorHandler) {
            delay(5000)
            handleResponseFee(repository.getFee(asset))
        }
    }

    fun sendMax(asset: String, rate: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            val data = repository.sendMax(asset, rate.replace(",", ".", true))

            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            if (data.result == false) {
                messageError.postValue(data.error?.message ?: "Error")
            } else {
                handleResponseSendMax(data)
            }

        }
    }

    private fun handleResponseFee(fee: FeeResponse) {
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