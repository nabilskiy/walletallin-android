package com.tallin.wallet.ui.kyc.kyc

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.BuildConfig
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.KycResponse
import com.tallin.wallet.data.response.ProfileResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class KycViewModel(
    app: Application,
    private val repository: KycRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val ldProfile = MutableLiveData<ProfileResponse>()
    val result = MutableLiveData<Array<String>>()

    private var getProfileJob: Job? = null
    private var kycJob: Job? = null

    fun getProfile() {
        getProfileJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val getProfile = repository.getProfile()
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
            handleResponseGetProfile(getProfile)
        }
    }

    private fun handleResponseGetProfile(profile: ProfileResponse) {
        if(profile.result == true) {
            ldProfile.postValue(profile)
            sharedPreferencesProvider.setUser(profile.user)
            return
        }

        showError.postValue(profile.error?.message ?: "Error")
    }

    override fun stopRequest() {
        getProfileJob?.cancel()
        kycJob?.cancel()
    }

    override fun onErrorHandler(throwable: Throwable) {
        showError.postValue("Error")
        Log.e("!!!", "Error")
        Log.e("!!!", throwable.toString())
        Log.e("!!!", throwable.message.toString())
    }

    fun getKycLink(flowName: String){
        if (sharedPreferencesProvider.getUser() != null){
            kycJob = launch(::onErrorHandler) {
                withContext(Dispatchers.Main) { onStartProgress.value = Unit }
                println("getKycLink START")
                val partMap1 = hashMapOf<String, Any>()
                val partMap2 = hashMapOf<String, Any>()
                val partMap3 = hashMapOf<String, Any>()
                partMap3["onComplete"] = BuildConfig.API_URL+"getid/complete?externalId="+sharedPreferencesProvider.getUser()!!.idp
                partMap3["onError"] = BuildConfig.API_URL+"getid/error?error_code={errorCode}"
                partMap2["First name"] = sharedPreferencesProvider.getUser()!!.firstName!!
                partMap2["Last name"] = sharedPreferencesProvider.getUser()!!.lastName!!
                partMap1["profile"] = partMap2
                partMap1["redirects"] = partMap3

                handleResponse(repository.getKycLink(
                    sharedPreferencesProvider.getUser()!!.kycProgram!!.getIdUrl!!,
                    sharedPreferencesProvider.getUser()!!.idp!!,
                    partMap1,
                    flowName,
                    sharedPreferencesProvider.getUser()!!.kycProgram!!.apiKeyGetId!!
                ))
                println("getKycLink STOP")
                withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            }
        }
    }
    private fun handleResponse(link: KycResponse) {
        if(link.url != null) {
            val arr: Array<String> = arrayOf(link.url, sharedPreferencesProvider.getUser()!!.idp!!)
            result.postValue(arr)
            return
        }

        showError.postValue("Error")
    }
}