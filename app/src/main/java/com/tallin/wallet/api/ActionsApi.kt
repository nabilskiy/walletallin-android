package com.tallin.wallet.api

import com.tallin.wallet.data.requests.BuySellRequest
import com.tallin.wallet.data.requests.RateRequest
import com.tallin.wallet.data.response.*
import retrofit2.http.*

interface ActionsApi {

    @GET("/api/exchange/rates/{currency}")
    suspend fun getRate(
        @Path("currency") currency: String
      //  @Query("currency") currency: String = ""//@Body body: RateRequest
    ): RateResponse

    @POST("/api/exchange/buy")
    suspend fun buy(
        @Body body: BuySellRequest
    ): BuySellResponse

    @POST("/api/exchange/sell")
    suspend fun sell(
        @Body body: BuySellRequest
    ): BuySellResponse

    @GET("/api/exchange/currencies")
    suspend fun currencies(): GetCurrenciesResponse

    @GET("/api/exchange/calculate")
    suspend fun  calculateAPI(
        @Query("rate") rate: Double,
        @Query("amountFiat") amountFiat: Double? = null,
        @Query("amountCrypto") amountCrypto: Double? = null,
        @Query("currencyTo") currencyTo: String
    ): CalculateRateResponse

    @POST("/api/exchange/buy")
    suspend fun confirmBuy(
        @Body body: ConfirmBuySellRequest
    ): BuySellResponse

    @POST("/api/exchange/sell")
    suspend fun confirmSell(
        @Body body: ConfirmBuySellRequest
    ): BuySellResponse
}