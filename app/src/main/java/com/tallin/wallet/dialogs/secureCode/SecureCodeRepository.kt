package com.tallin.wallet.dialogs.secureCode

import com.tallin.wallet.api.UserApi
import com.tallin.wallet.data.EditProfileRequest

class SecureCodeRepository(private val api: UserApi) {

    suspend fun updateUser(body: EditProfileRequest) = api.editProfile(body)
}