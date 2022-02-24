package com.tallin.wallet.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.requests.LoginRequest
import com.tallin.wallet.data.response.ProfileResponse
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.utils.crypto.encryptData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, repository) {

    val resultLogin = MutableLiveData<Boolean>()
    val resultGetProfile = MutableLiveData<Boolean>()

    private var loginJob: Job? = null
    private var getProfileJob: Job? = null

    override fun stopRequest() {
        loginJob?.cancel()
        getProfileJob?.cancel()
    }

    fun login(email: String, password: String) {
        loginJob = launch(::handleError) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val login = repository.login(LoginRequest(email, encryptData(password)))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
            Log.e("!!!", login.toString())

            sharedPreferencesProvider.saveLogin(email)
            sharedPreferencesProvider.savePassword(password)
            handleResponseLogin(login)

        }
    }

    fun getProfile() {
        getProfileJob = launch(::handleError) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val getProfile = repository.getProfile()
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
            Log.e("!!!", getProfile.toString())
            handleResponseGetProfile(getProfile)

        }
    }

    private fun handleResponseLogin(signUp: SignUpResponse) {
        if(signUp.result == true) {
            sharedPreferencesProvider.setToken(signUp.token ?: "")
            resultLogin.postValue(true)
            return
        }

        showError.postValue(signUp.error?.message ?: "Error")
    }

    private fun handleResponseGetProfile(profile: ProfileResponse) {
        if(profile.result == true) {
            resultGetProfile.postValue(true)
            sharedPreferencesProvider.setUser(profile.user)
            sharedPreferencesProvider.saveKycStatus(false)
            /*if (profile.user?.wallet?.kyc_program_status != null)
                if (profile.user.wallet.kyc_program_status == 0) {
                    sharedPreferencesProvider.saveKycStatus(3)
                } else sharedPreferencesProvider.saveKycStatus(profile.user.wallet.kyc_program_status)*/
            return
        }

        showError.postValue(profile.error?.message ?: "Error")
    }

    private fun handleError(t: Throwable) {
        resultLogin.postValue(false)
    }


}