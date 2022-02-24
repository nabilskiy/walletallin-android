package com.tallin.wallet.ui.actions.orderPreview

import com.tallin.wallet.api.ActionsApi
import com.tallin.wallet.data.requests.BuyRequest
import com.tallin.wallet.data.requests.SellRequest

class OrderPreviewRepository(private val api: ActionsApi) {

    suspend fun buy(cryptoCurrency: String, cryptoAmount: Double) = api.buy(BuyRequest(cryptoCurrency, cryptoAmount))
    suspend fun sell(fiatCurrency: String, fiatAmount: Double) = api.sell(SellRequest(fiatCurrency, fiatAmount))
}