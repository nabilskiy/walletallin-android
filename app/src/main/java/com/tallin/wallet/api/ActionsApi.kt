package com.tallin.wallet.api

import com.tallin.wallet.data.requests.BuyRequest
import com.tallin.wallet.data.requests.RateRequest
import com.tallin.wallet.data.requests.SellRequest
import com.tallin.wallet.data.response.BuySellResponse
import com.tallin.wallet.data.response.RateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ActionsApi {

    @POST("/api/exchange/get-rate")
    suspend fun getRate(
        @Body body: RateRequest
    ): RateResponse

    @POST("/api/exchange/buy")
    suspend fun buy(
        @Body body: BuyRequest
    ): BuySellResponse

    @POST("/api/exchange/sell")
    suspend fun sell(
        @Body body: SellRequest
    ): BuySellResponse
}