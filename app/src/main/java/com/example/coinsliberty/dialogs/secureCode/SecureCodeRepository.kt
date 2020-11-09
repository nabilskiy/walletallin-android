package com.example.coinsliberty.dialogs.secureCode

import com.example.coinsliberty.api.UserApi
import com.example.coinsliberty.api.WalletApi
import com.example.coinsliberty.data.EditProfileRequest

class SecureCodeRepository(private val api: UserApi) {

    suspend fun updateUser(body: EditProfileRequest) = api.editProfile(body)
}