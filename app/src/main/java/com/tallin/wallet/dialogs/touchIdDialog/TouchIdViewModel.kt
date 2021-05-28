package com.tallin.wallet.dialogs.touchIdDialog


import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository

class TouchIdViewModel(
    app: Application,
    private val repository: TouchIdRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val timeToDismiss: MutableLiveData<Boolean> = MutableLiveData()

    override fun stopRequest() {

    }
}