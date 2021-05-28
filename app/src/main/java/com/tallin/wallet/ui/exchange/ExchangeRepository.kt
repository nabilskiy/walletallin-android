package com.tallin.wallet.ui.exchange

import com.tallin.wallet.api.ExchangeApi
import com.tallin.wallet.data.SwapRequestBody

class ExchangeRepository(private val api: ExchangeApi) {

    suspend fun getLimits(from: String, to: String) = api.getLimits(from, to)

    suspend fun getExchangeInfo(
        from: String,
        to: String,
        amountFrom: String?,
        amountTo: String?
    ) =
        api.getInfo(from, to, amountFrom, amountTo)

    suspend fun doSwap(swapRequestBody: SwapRequestBody) = api.doSwap(swapRequestBody)

    suspend fun getSwapInfo(id:String) = api.getSwapInfo(id)


}