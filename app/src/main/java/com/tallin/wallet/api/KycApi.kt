package com.tallin.wallet.api

import com.tallin.wallet.data.response.KycManuallyResponse
import com.tallin.wallet.data.response.KycResponse
import okhttp3.MultipartBody
import retrofit2.http.*

//additional api
interface KycApi {

    @POST("api/v1/tokenized-url")
    suspend fun tokenized(
        @Query("externalId") externalId: String,
        @Body partMap: HashMap<String, Any>,
        @Query("name") name: String,
        @Header("X-API-Key") key: String
    ): KycResponse
}
// main api
interface KycMainApi {

    @Multipart
    @POST("/api/upload")
    suspend fun sendDoc(
        @Part assign_id: MultipartBody.Part,
        @Part document_id: MultipartBody.Part,
        //@Query("body") SendDoc: SendDoc,
        //@Query("document_id") documentId: Int,
        @Part body: MultipartBody.Part
    ): KycManuallyResponse
}

/*data class SendDoc(
    @SerializedName("assign_id")
    var assignId: Int,
    @SerializedName("document_id")
    var documentId: Int
)*/
