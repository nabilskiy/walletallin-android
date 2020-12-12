package com.coinsliberty.wallet.dialogs.touchIdDialog


import android.app.Application
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.utils.biometric.BiometricHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TouchIdViewModel(
    app: Application,
    private val repository: TouchIdRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val timeToDismiss: MutableLiveData<Boolean> = MutableLiveData()

    override fun stopRequest() {

    }
}