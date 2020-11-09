package com.example.coinsliberty.dialogs

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.AddressInfoResponse
import com.example.coinsliberty.data.ForgotPassRequest
import com.example.coinsliberty.data.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QrCodeViewModel(
    app: Application,
    private val repository: QrCodeRepository
) : BaseViewModel(app) {

    val resultRecovery = MutableLiveData<String>()

    fun getAddress() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.getAddress())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(address: AddressInfoResponse) {
        if(address.result == true) {
            resultRecovery.postValue(address.address)
            return
        }
        showError.postValue(address.error?.message)
    }

}