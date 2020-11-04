package com.example.coinsliberty.ui.settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.SignUpResponse
import com.example.coinsliberty.model.SharedPreferencesProvider
import com.example.coinsliberty.ui.config.NavigationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsViewModel(private val app: Application, private val sharedPreferencesProvider: SharedPreferencesProvider,private val settingsRepository: SettingsRepository): BaseViewModel(app) {

    val ldLogout = MutableLiveData<Boolean>()

    fun logout() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(settingsRepository.logout())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(logout: SignUpResponse) {
        if(logout.result == true) {
            sharedPreferencesProvider.setToken("")
            ldLogout.postValue(true)

            return
        }
        showError.postValue("Can not logout")
    }

}