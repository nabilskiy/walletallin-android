package com.coinsliberty.wallet.api

import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.response.FeeResponse
import com.coinsliberty.wallet.data.response.SendMaxResponse
import com.coinsliberty.wallet.data.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BtcApi {
    @POST("/api/finance/send")
    suspend fun sendBtcRate(
        @Body body: BtcBalance
    ): SignUpResponse

    @GET("/api/fees")
    suspend fun getFee(): FeeResponse

    @GET("/api/calc_withdraw_all")
    suspend fun sendMax(
        @Query("asset") asset: String,
        @Query("rate") rate: String
    ): SendMaxResponse

}