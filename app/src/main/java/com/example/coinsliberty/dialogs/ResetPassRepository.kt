package com.example.coinsliberty.dialogs

import com.example.coinsliberty.api.UserApi
import com.example.coinsliberty.data.ChangePasswordRequest

class ResetPassRepository(private val api: UserApi) {

    suspend fun changePassword(data: ChangePasswordRequest) = api.changePassword(data)
}