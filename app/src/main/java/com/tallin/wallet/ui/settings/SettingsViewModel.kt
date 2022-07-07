package com.tallin.wallet.ui.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.tallin.wallet.BuildConfig
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.data.response.UserResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.ui.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val settingsRepository: SettingsRepository,
    private val profileRepository: ProfileRepository,
    private val loginRepository: LoginRepository
    ): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val ldLogout = MutableLiveData<Boolean>()
    val ldAva = MutableLiveData<Long>()
    val ldUser = MutableLiveData<Boolean>()

    override fun stopRequest() {

    }

    fun logout() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(SignUpResponse(result = true))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun loadProfile() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            try {
                sharedPreferencesProvider.setUser(profileRepository.getProfile().user!!)
            }catch (e: Exception){}
            sharedPreferencesProvider.getUser()?.avatar.let {
                ldAva.postValue(it)
            }
            ldUser.postValue(true)
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(logout: SignUpResponse) {
        if(logout.result == true) {
            sharedPreferencesProvider.setToken("")
            sharedPreferencesProvider.savePassword("")
            baseLogout.postValue(false)

            return
        }
        if(logout.error?.code == 1002) {
            baseLogout.postValue(true)

            return
        }
        showError.postValue("Can not logout")
    }

    fun getUserAvatarGlideUrl(id: Long): GlideUrl {

        Log.e("!!!", sharedPreferencesProvider.getToken() ?: "")
        return GlideUrl(
            "${BuildConfig.API_RES_URL}files/${id}",
            LazyHeaders.Builder()
                .addHeader("slc-auth", sharedPreferencesProvider.getToken() ?: "")
                .build()
        )
    }

    fun getUserStatus(): Int{
        return sharedPreferencesProvider.getUser()?.wallet?.kycProgramStatus ?: 9
    }

    fun getUser(): UserResponse? {
        return sharedPreferencesProvider.getUser()
    }
}