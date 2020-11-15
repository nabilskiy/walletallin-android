package com.coinsliberty.wallet.dialogs.ressPassword

import com.coinsliberty.wallet.api.UserApi
import com.coinsliberty.wallet.data.ChangePasswordRequest

class ResetPassRepository(private val api: UserApi) {

    suspend fun changePassword(data: ChangePasswordRequest) = api.changePassword(data)
}