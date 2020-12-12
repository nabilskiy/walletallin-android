package com.coinsliberty.wallet.ui.settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val settingsRepository: SettingsRepository,
    private val loginRepository: LoginRepository
    ): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val ldLogout = MutableLiveData<Boolean>()

    override fun stopRequest() {

    }

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
            baseLogout.postValue(false)

            return
        }
        if(logout.error?.code == 1002) {
            baseLogout.postValue(true)

            return
        }
        showError.postValue("Can not logout")
    }

}