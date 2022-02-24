package com.tallin.wallet.api

import com.tallin.wallet.data.response.KycManuallyResponse
import com.tallin.wallet.data.response.KycResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface KycApi {

    @POST()
    suspend fun tokenized(
        @Url() url: String,
        @Query("externalId") externalId: String,
        @Body partMap: HashMap<String, Any>,
        @Query("name") name: String,
        @Header("X-API-Key") key: String
    ): KycResponse

    @Multipart
    @POST("/api/upload")
    suspend fun sendDoc(
        @Part assign_id: MultipartBody.Part,
        @Part document_id: MultipartBody.Part,
        @Part body: MultipartBody.Part
    ): KycManuallyResponse
}
