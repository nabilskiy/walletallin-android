package com.coinsliberty.wallet.api

import com.coinsliberty.wallet.data.SwapRequestBody
import com.coinsliberty.wallet.data.response.SwapInfoResponse
import com.coinsliberty.wallet.data.response.SwapLimitsResponse
import com.coinsliberty.wallet.data.response.SwapResultResponse
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExchangeApi {

    @GET("/mapi/swap/limits")
    suspend fun getLimits(
        @Query("from") from: String,
        @Query("to") to: String
    ): SwapLimitsResponse

    @GET("/mapi/swap/info")
    suspend fun getInfo(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amountFrom") amountFrom: String?,
        @Query("amountTo") amountTo: String?
    ): SwapInfoResponse

    @POST("/mapi/swap")
    suspend fun doSwap(@Body body: SwapRequestBody): SwapResultResponse

    @GET("/mapi/swap")
    suspend fun getSwapInfo(
        @Query("id") id: String
    ): JsonObject


}