package com.tallin.wallet.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.tallin.wallet.BuildConfig
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.requests.EditProfileRequest
import com.tallin.wallet.data.response.ProfileResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.utils.currency.Currency
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

    fun getUserAvatarGlideUrl(id: Long) : GlideUrl {

        Log.e("!!!", sharedPreferencesProvider.getToken() ?: "")
        val glideUrl = GlideUrl(
            "${BuildConfig.API_RES_URL}files/${id}",
            LazyHeaders.Builder()
                .addHeader("slc-auth",  sharedPreferencesProvider.getToken() ?: "")
                .build()
        )
        return glideUrl
    }

    private fun setUserAvatar(idPhoto: Long) {
        ldCurrentUserAvatarId.postValue(idPhoto)
    }

    fun getProfile() {
        getProfileJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            ldProfile.postValue(repository.getProfile())
            ldProfile.value?.user?.avatar?.let { setUserAvatar(it) }
            val profile = ldProfile.value
            if (profile?.result == true) {
                sharedPreferencesProvider.setUser(profile.user)
            }
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }
    fun editProfile(firstName: String, lastName: String, phone: String, avatar: Long? = null) {
        editProfileJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.editProfile(
                EditProfileRequest(firstName, lastName, phone,
                avatar = ldCurrentUserAvatarId.value
            )
            )
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
                ldCurrentUserAvatarId.postValue(response.item?.values?.iterator()?.next() ?: 0)
            }
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