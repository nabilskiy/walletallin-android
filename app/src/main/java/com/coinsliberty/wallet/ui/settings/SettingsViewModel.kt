package com.coinsliberty.wallet.ui.settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsViewModel(private val app: Application, private val sharedPreferencesProvider: SharedPreferencesProvider,private val settingsRepository: SettingsRepository): BaseViewModel(app) {

    val ldLogout = MutableLiveData<Boolean>()

    fun logout() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(SignUpResponse(result = true))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(logout: SignUpResponse) {
        if(logout.result == true) {
            sharedPreferencesProvider.setToken("")
            baseLogout.postValue(true)

            return
        }
        if(logout.error?.code == 1002) {
            baseLogout.postValue(true)

            return
        }
        showError.postValue("Can not logout")
    }

}