package com.coinsliberty.wallet.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.EditProfileRequest
import com.coinsliberty.wallet.data.response.ProfileResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.utils.currency.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val app: Application,
    private val repository: ProfileRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val ldProfile = MutableLiveData<ProfileResponse>()
    val ldOtp = MutableLiveData<String>()
    val ldCurrency = MutableLiveData<Currency>()

    fun getProfile() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            ldProfile.postValue(repository.getProfile())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }
    fun editProfile(firstName: String, lastName: String, phone: String, optEnabled: Boolean, file: Any?, otp: String? = null) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.editProfile(EditProfileRequest(firstName, lastName, phone, optEnabled, otp = otp))
            if(response.result == false) {
                showError.postValue(response.error?.message ?: "Error")
            }
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun getOtp() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.getOtp()
            ldOtp.postValue(response.token)
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun getCurrency(){
        ldCurrency.postValue(sharedPreferencesProvider.getCurrency())
    }

    fun saveCurrency(currency: Currency) {
        sharedPreferencesProvider.saveCurrency(currency)
        getCurrency()
    }

    override fun onErrorHandler(throwable: Throwable) {
        showError.postValue("Error")
    }


}