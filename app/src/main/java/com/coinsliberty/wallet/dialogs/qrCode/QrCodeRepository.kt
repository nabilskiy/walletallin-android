package com.coinsliberty.wallet.dialogs.qrCode

import com.coinsliberty.wallet.api.WalletApi

class QrCodeRepository(private val api: WalletApi) {

    suspend fun getAddress() = api.getAddress()
}