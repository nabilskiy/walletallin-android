package com.example.coinsliberty.ui.profile

import android.app.Application
import android.util.Log
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.EditProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileViewModel(private val app: Application, private val repository: ProfileRepository) :
    BaseViewModel(app) {

    fun editProfile(firstName: String, lastName: String, phone: String, optEnabled: Boolean, file: Any?) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.editProfile(EditProfileRequest(firstName, lastName, phone, optEnabled, file))
            Log.e("!!!", response.toString())


            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

}