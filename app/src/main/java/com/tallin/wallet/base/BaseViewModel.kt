package com.tallin.wallet.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tallin.wallet.data.LoginRequest
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.utils.coroutines.CoroutineHelper
import com.tallin.wallet.utils.crypto.encryptData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
abstract class BaseViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
    ) : AndroidViewModel(app) {

    val onStartProgress: MutableLiveData<Unit> = MutableLiveData()
    val onEndProgress: MutableLiveData<Unit> = MutableLiveData()

    var showError: MutableLiveData<String> = MutableLiveData()

    val showDialog: MutableLiveData<Unit> = MutableLiveData()

    val restart: MutableLiveData<Unit> = MutableLiveData()

    val baseLogout: MutableLiveData<Boolean> = MutableLiveData()

    //val anyData: MutableLiveData<Any> = MutableLiveData()


    private var coroutineHelper: CoroutineHelper

    init {
        coroutineHelper = CoroutineHelper(viewModelScope)
    }

    protected open fun launch(
        onError: (e: Throwable) -> Unit,
        checkInternetConnection: Boolean = true,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit): Job {
        if(coroutineHelper == null) coroutineHelper = CoroutineHelper(viewModelScope)
        return coroutineHelper.launch(checkInternetConnection, coroutineContext, app, block, onError)
    }



    open fun onErrorHandler(throwable: Throwable) {

        // TODO: 23.02.2021 DMRK!!! stop lottie animation when request failed
        onEndProgress.value = Unit

        //Toast.makeText(app, throwable.message, Toast.LENGTH_SHORT).show()
//        val body = (throwable as HttpException).response()?.errorBody()

//        val gson = Gson()
//        val adapter = gson.getAdapter(Any::class.java)
//        try {
//            val errorParser = adapter.fromJson(body?.string())
//            Toast.makeText(app, errorParser.message.toString(), Toast.LENGTH_SHORT).show()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }

    fun relogin() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val login = loginRepository.login(LoginRequest(sharedPreferencesProvider.getLogin(), encryptData(sharedPreferencesProvider.getPassword())))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
            if(login.result == false) {
                showError.postValue(login.error?.message ?: "")
            } else {
                handleResponse(login)
            }
        }
    }

    private fun handleResponse(login: SignUpResponse) {
        sharedPreferencesProvider.setToken(login.token ?: "")
        restart.postValue(Unit)
    }

    abstract fun stopRequest();

}

