package com.tallin.wallet.dialogs.ressPassword

import com.tallin.wallet.api.UserApi
import com.tallin.wallet.data.ChangePasswordRequest

class ResetPassRepository(private val api: UserApi) {

    suspend fun changePassword(data: ChangePasswordRequest) = api.changePassword(data)
}