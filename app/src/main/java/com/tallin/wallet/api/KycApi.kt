package com.tallin.wallet.api

import com.tallin.wallet.data.response.KycResponse
import retrofit2.http.*

interface KycApi {

    @POST("api/v1/tokenized-url")
    suspend fun tokenized(
        @Query("externalId") externalId: String,
        @Body partMap: HashMap<String, Any>,
        @Query("name") name: String = "getid-doc-only",
        @Header("X-API-Key") key: String = "5c58dee722bb51700c1302d39529d3f941cbafeb3ea71592ee292b0bba41f219"
    ): KycResponse
}
