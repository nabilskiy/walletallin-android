package com.tallin.wallet.ui.singup.singupBusiness

import com.tallin.wallet.api.LoginApi
import com.tallin.wallet.data.SignUpRequest

class SingUpBusinessRepository(private val api: LoginApi) {

    suspend fun signUp(data: SignUpRequest) = api.signUp(data)
}