package com.coinsliberty.wallet.dialogs.qrCode

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.AddressInfoResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QrCodeViewModel(
    app: Application,
    private val repository: QrCodeRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

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