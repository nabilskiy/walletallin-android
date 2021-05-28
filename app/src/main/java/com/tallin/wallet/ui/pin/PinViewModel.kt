package com.tallin.wallet.ui.pin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.utils.manager.BiometricManager

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