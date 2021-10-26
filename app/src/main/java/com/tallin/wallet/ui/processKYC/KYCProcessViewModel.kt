package com.tallin.wallet.ui.processKYC

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.ProfileKycRequest
import com.tallin.wallet.data.response.KycResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class KYCProcessViewModel(
    app: Application,
    private val repository: KYCProcessRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val result = MutableLiveData<Array<String>>()

    private var kycJob: Job? = null

    fun getKycLink(){
        if (sharedPreferencesProvider.getUser()?.idp != null &&
            sharedPreferencesProvider.getUser()?.firstName != null &&
            sharedPreferencesProvider.getUser()?.lastName != null){
            kycJob = launch(::onErrorHandler) {
                withContext(Dispatchers.Main) { onStartProgress.value = Unit }
                val partMap1 = hashMapOf<String, Any>()
                val partMap2 = hashMapOf<String, Any>()
                val partMap3 = hashMapOf<String, Any>()
                partMap3["onComplete"] = "https://wallet-stage.walletallin.com/getid/complete?externalId="+sharedPreferencesProvider.getUser()!!.idp
                partMap3["onError"] = "https://wallet-stage.walletallin.com/getid/error?error_code={errorCode}"
                partMap2["First name"] = sharedPreferencesProvider.getUser()!!.firstName!!
                partMap2["Last name"] = sharedPreferencesProvider.getUser()!!.lastName!!
                partMap1["profile"] = partMap2
                partMap1["redirects"] = partMap3

                handleResponse(repository.getKycLink(
                    sharedPreferencesProvider.getUser()!!.idp!!, partMap1
                ))
                withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            }
        }
    }
    override fun stopRequest() {
        kycJob?.cancel()
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