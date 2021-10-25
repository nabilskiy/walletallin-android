package com.tallin.wallet.ui.singup.singupBusiness

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.SignUpRequest
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.data.response.WalletTypesData
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
        walletTypeId: String,
        name: String,
        companyNumber: String,
        phone: String,
        website: String,
        firstNameDirector: String,
        lastNameDirector: String,
        city: String,
        street: String,
        postalCode: String,
        country: String,
        description: String,
        email: String,
        password: String
    ) {
        signUpJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.signUp(SignUpRequest(
                wallet_type_id =
               /* if (anyData.value is ArrayList<*> && (anyData.value as ArrayList<*>)[1] is WalletTypesData) //todo
                    ((anyData.value as ArrayList<*>)[1] as WalletTypesData).type_id.toString()
                else*/ walletTypeId,
                name = name,
                company_number = companyNumber,
                phone = phone,
                website = website,
                first_name_director = firstNameDirector,
                last_name_director = lastNameDirector,
                city = city,
                street = street,
                postal_code = postalCode,
                country = country,
                description = description,
                email = email,
                company_email = email,
                password = password,
                first_name = firstNameDirector,
                last_name = lastNameDirector
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