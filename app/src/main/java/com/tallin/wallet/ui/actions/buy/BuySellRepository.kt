package com.tallin.wallet.ui.actions.buy

import com.tallin.wallet.api.ActionsApi
import com.tallin.wallet.api.WalletApi
import com.tallin.wallet.data.requests.RateRequest

class BuySellRepository(private val api: WalletApi, private val api2: ActionsApi) {

    suspend fun walletList() = api.walletList()

    suspend fun getBalance() = api.getBalance()

    suspend fun getRate(currency: String) = api2.getRate(currency)

    suspend fun currencies() = api2.currencies()

    suspend fun calculateAPI(
        rate: Double,
        amountFiat: Double?,
        amountCrypto: Double?,
        currencyTo: String
    ) = api2.calculateAPI(rate, amountFiat, amountCrypto, currencyTo)
}