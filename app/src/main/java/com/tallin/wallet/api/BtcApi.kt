package com.tallin.wallet.api

import com.tallin.wallet.data.BtcBalance
import com.tallin.wallet.data.response.FeeResponse
import com.tallin.wallet.data.response.SendMaxResponse
import com.tallin.wallet.data.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BtcApi {
    @POST("/mapi/finance/send")
    suspend fun sendBtcRate(
        @Body body: BtcBalance
    ): SignUpResponse

    @GET("/api/fees")
    suspend fun getFee(@Query("asset") asset: String): FeeResponse

    @GET("/api/get_withdraw_all_values")
    suspend fun sendMax(
        @Query("asset") asset: String,
        @Query("fee") rate: String
    ): SendMaxResponse

}