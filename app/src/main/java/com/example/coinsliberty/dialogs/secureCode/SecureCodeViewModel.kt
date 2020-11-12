package com.example.coinsliberty.dialogs.secureCode

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.EditProfileRequest
import com.example.coinsliberty.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SecureCodeViewModel(
    app: Application,
    private val repository: SecureCodeRepository
) : BaseViewModel(app) {

    val resultRecovery = MutableLiveData<Boolean>()

    fun updateProfile(account: EditProfileRequest?) {
        if(account == null) return
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.updateUser(account))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(address: SignUpResponse) {
        if(address.result == true) {
            resultRecovery.postValue(address.result)
            return
        }
        showError.postValue(address.error?.message)
    }

}