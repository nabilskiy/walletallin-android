package com.coinsliberty.wallet.ui.pin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.SignUpResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PinViewModel(
    app: Application,
    private val repository: PinRepository
) : BaseViewModel(app) {

}