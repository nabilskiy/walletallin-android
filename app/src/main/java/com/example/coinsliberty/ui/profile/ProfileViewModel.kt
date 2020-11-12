package com.example.coinsliberty.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.EditProfileRequest
import com.example.coinsliberty.data.response.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileViewModel(private val app: Application, private val repository: ProfileRepository) :
    BaseViewModel(app) {

    val ldProfile = MutableLiveData<ProfileResponse>()
    val ldOtp = MutableLiveData<String>()

    fun getProfile() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            ldProfile.postValue(repository.getProfile())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }
    fun editProfile(firstName: String, lastName: String, phone: String, optEnabled: Boolean, file: Any?) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.editProfile(EditProfileRequest(firstName, lastName, phone, optEnabled, otp = null))
            Log.e("!!!", response.toString())


            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun getOtp() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.getOtp()
            Log.e("!!!", response.token ?: "")
            ldOtp.postValue(response.token)
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    override fun onErrorHandler(throwable: Throwable) {
        showError.postValue("Error")
    }


}