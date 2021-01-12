package com.coinsliberty.wallet.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.coinsliberty.wallet.BuildConfig
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.EditProfileRequest
import com.coinsliberty.wallet.data.response.ProfileResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import com.coinsliberty.wallet.utils.currency.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class ProfileViewModel(
    private val app: Application,
    private val repository: ProfileRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val ldProfile = MutableLiveData<ProfileResponse>()
    val ldOtp = MutableLiveData<String>()
    val ldCurrency = MutableLiveData<Currency>()
    val ldCurrentUserAvatarId = MutableLiveData<Long>()

    private var getProfileJob: Job? = null
    private var editProfileJob: Job? = null
    private var getOtpJob: Job? = null

    override fun stopRequest() {
        getProfileJob?.cancel()
        editProfileJob?.cancel()
        getOtpJob?.cancel()
    }

    fun getUserAvatarGlideUrl() : GlideUrl {
        val glideUrl = GlideUrl(
            "${BuildConfig.API_URL}files/${160}",
            LazyHeaders.Builder()
                .addHeader("slc-auth",  sharedPreferencesProvider.getToken() ?: "")
                .build()
        )
        return glideUrl
    }

    fun setUserAvatar(idPhoto: Long) {
        ldCurrentUserAvatarId.postValue(idPhoto)
    }

    fun getProfile() {
        getProfileJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            ldProfile.postValue(repository.getProfile())
            ldProfile.value?.user?.avatar?.let { setUserAvatar(it) }
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }
    fun editProfile(firstName: String, lastName: String, phone: String, optEnabled: Boolean, file: Any?, otp: String? = null, avatar: Long? = null) {
        editProfileJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.editProfile(EditProfileRequest(firstName, lastName, phone, optEnabled, otp = otp,
                avatar = avatar
            ))
            if(response.result == false) {
                showError.postValue(response.error?.message ?: "Error")
            }
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun sendFile(file: MultipartBody.Part) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.sendFile(file)
            if(response.result == true) {
                ldCurrentUserAvatarId.postValue(response.item?.file)
                Log.e("new avatar!!!", response.item?.file.toString())
            }
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun getOtp() {
        getOtpJob = launch(::onErrorHandler) {
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
        Log.e("!!!", "Error")
        Log.e("!!!", throwable.toString())
        Log.e("!!!", throwable.message.toString())
    }

}