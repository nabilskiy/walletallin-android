package com.tallin.wallet.ui.singup.singupBusiness

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.SignUpRequest
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class SignUpBusinessViewModel(
    app: Application,
    private val repository: SingUpBusinessRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val result = MutableLiveData<Boolean>()

    var signUpJob: Job? = null

    fun signUp(
        wallet_type_id: String,
        name: String,
        company_number: String,
        phone: String,
        website: String,
        first_name_director: String,
        last_name_director: String,
        city: String,
        street: String,
        postal_code: String,
        country: String,
        description: String
    ) {
        signUpJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.signUp(SignUpRequest(
                wallet_type_id,
                name,
                company_number,
                phone,
                website,
                first_name_director,
                last_name_director,
                city,
                street,
                postal_code,
                country,
                description
            )))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    override fun stopRequest() {
        signUpJob?.cancel()
    }

    fun showError(error: String) {
        showError.postValue(error)
    }

    private fun handleResponse(signUp: SignUpResponse) {
        if(signUp.result == true) {
            result.postValue(true)
            return
        }

        showError.postValue(signUp.error?.message ?: "Error")
    }
}