package com.tallin.wallet.api

import com.tallin.wallet.data.response.KycResponse
import retrofit2.http.*

interface KycApi {

    @POST("api/v1/tokenized-url")
    suspend fun tokenized(
        @Query("externalId") externalId: String,
        @Body partMap: HashMap<String, Any>,
        @Query("name") name: String,
        @Header("X-API-Key") key: String
    ): KycResponse
}
