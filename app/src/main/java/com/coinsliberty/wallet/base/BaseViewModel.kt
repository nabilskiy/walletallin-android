package com.coinsliberty.wallet.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.coinsliberty.wallet.utils.coroutines.CoroutineHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(private val app: Application) : AndroidViewModel(app) {

    val onStartProgress: MutableLiveData<Unit> = MutableLiveData()
    val onEndProgress: MutableLiveData<Unit> = MutableLiveData()

    val showError: MutableLiveData<String> = MutableLiveData()

    val baseLogout: MutableLiveData<Boolean> = MutableLiveData()


    private val coroutineHelper = CoroutineHelper(viewModelScope)

    protected open fun launch(
        onError: (e: Throwable) -> Unit,
        checkInternetConnection: Boolean = true,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit) = coroutineHelper.launch(checkInternetConnection, coroutineContext, app, block, onError)


    open fun onErrorHandler(throwable: Throwable) {
        Log.e("!!!", "error")
        Log.e("!!!", throwable.message.toString())
//        Toast.makeText(app, throwable.message, Toast.LENGTH_SHORT).show()
//        val body = (throwable as HttpException).response()?.errorBody()
//
//        val gson = Gson()
//        val adapter = gson.getAdapter(Any::class.java)
//        try {
//            val errorParser = adapter.fromJson(body?.string())
//            Toast.makeText(app, errorParser.message.toString(), Toast.LENGTH_SHORT).show()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }
}