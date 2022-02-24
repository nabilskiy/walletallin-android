package com.tallin.wallet.ui.kyc.processKYC

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.BuildConfig
import com.tallin.wallet.base.BaseViewModel
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

    override fun stopRequest() {
      // kycJob?.cancel()
    }
}