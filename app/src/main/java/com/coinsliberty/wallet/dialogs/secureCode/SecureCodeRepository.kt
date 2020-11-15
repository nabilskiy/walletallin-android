package com.coinsliberty.wallet.dialogs.secureCode

import com.coinsliberty.wallet.api.UserApi
import com.coinsliberty.wallet.data.EditProfileRequest

class SecureCodeRepository(private val api: UserApi) {

    suspend fun updateUser(body: EditProfileRequest) = api.editProfile(body)
}