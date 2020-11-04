package com.example.coinsliberty.dialogs

import com.example.coinsliberty.api.LoginApi
import com.example.coinsliberty.api.WalletApi
import com.example.coinsliberty.data.ForgotPassRequest

class QrCodeRepository(private val api: WalletApi) {

    suspend fun getAddress() = api.getAddress()
}