package com.coinsliberty.wallet.ui.pin

import android.app.Application
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.utils.manager.BiometricManager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PinViewModel(
    app: Application,
    private val repository: PinRepository,
    private val biometricManager: BiometricManager,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val goNext = MutableLiveData<Boolean>()

    fun getTypeOfBiometric() = biometricManager.getTypeOfBiometric()

    fun savePin(pin: String) {
        sharedPreferencesProvider.savePin(pin)
    }

    fun getPin() = sharedPreferencesProvider.getPin()

    override fun stopRequest() {

    }
}