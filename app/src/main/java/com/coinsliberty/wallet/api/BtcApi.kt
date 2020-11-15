package com.coinsliberty.wallet.api

import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BtcApi {
    @POST("/api/finance/send")
    suspend fun sendBtcRate(
        @Body body: BtcBalance
    ): SignUpResponse
}