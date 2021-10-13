package com.tallin.wallet.ui.singup.chooseWallet

import com.tallin.wallet.api.LoginApi

class SingUpChooseWalletRepository(private val api: LoginApi) {

    suspend fun getWalletTypes() = api.walletTypes()
}