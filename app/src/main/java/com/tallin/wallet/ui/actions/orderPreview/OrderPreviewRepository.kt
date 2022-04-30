package com.tallin.wallet.ui.actions.orderPreview

import com.tallin.wallet.api.ActionsApi
import com.tallin.wallet.data.requests.BuySellRequest
import com.tallin.wallet.data.response.ConfirmBuySellRequest

class OrderPreviewRepository(private val api: ActionsApi) {

    suspend fun buy(fiatCurrency: String, cryptoCurrency: String, amountFrom: String, amountTo: String) = api.buy(BuySellRequest(fiatCurrency, cryptoCurrency, amountFrom, amountTo))
    suspend fun sell(fiatCurrency: String, cryptoCurrency: String, amountFrom: String, amountTo: String) = api.sell(BuySellRequest(fiatCurrency, cryptoCurrency, amountFrom, amountTo))

    suspend fun confirmBuy(operationId: Int, code: String) = api.confirmBuy(ConfirmBuySellRequest(operationId, code))
    suspend fun confirmSell(operationId: Int, code: String) = api.confirmSell(ConfirmBuySellRequest(operationId, code))
}