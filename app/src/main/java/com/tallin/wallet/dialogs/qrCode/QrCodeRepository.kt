package com.tallin.wallet.dialogs.qrCode

import com.tallin.wallet.api.WalletApi

class QrCodeRepository(private val api: WalletApi) {

    suspend fun getAddress() = api.getAddressBtc()
}