package com.example.coinsliberty.api

import com.example.coinsliberty.data.BtcBalance
import com.example.coinsliberty.data.SignUpResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface BtcApi {
    @POST("/api/finance/send")
    suspend fun sendBtcRate(
        @Body body: BtcBalance
    ): SignUpResponse
}